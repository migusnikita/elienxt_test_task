package ru.mail.migus_nikta.service.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.mail.migus_nikta.annotation.Inject;
import ru.mail.migus_nikta.exception.BindingNotFoundException;
import ru.mail.migus_nikta.exception.ConstructorNotFoundException;
import ru.mail.migus_nikta.exception.TooManyConstructorsException;
import ru.mail.migus_nikta.service.Injector;
import ru.mail.migus_nikta.service.Provider;

public class InjectorImpl implements Injector {

    private static final Map<Class<?>, Object> container = new HashMap<>();

    @Override
    public <T> Provider<T> getProvider(Class<T> type) {
        @SuppressWarnings(value = "unchecked")
        T binding = (T) container.get(type);
        if (binding == null) {
            return null;
        } else {
            return new ProviderImpl<>(binding);
        }
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        List<Constructor<?>> annotatedConstructor = Arrays.stream(impl.getConstructors())
                .filter(o -> o.isAnnotationPresent(Inject.class))
                .collect(Collectors.toList());

        if (annotatedConstructor.size() > 1) {
            throw new TooManyConstructorsException();
        } else if (annotatedConstructor.size() == 0) {
            try {
                container.put(intf, impl.getDeclaredConstructor().newInstance());
            } catch (NoSuchMethodException e) {
                throw new ConstructorNotFoundException();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            Object[] objects = Arrays.stream(annotatedConstructor.get(0).getParameterTypes()).map(o -> {
                try {
                    return o.getDeclaredConstructor().newInstance();
                } catch (NoSuchMethodException e) {
                    throw new BindingNotFoundException();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            }).toArray();
            try {
                container.put(intf, annotatedConstructor.get(0).newInstance(objects));
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
        if (!container.containsKey(intf)) {
            bind(intf, impl);
        }
    }

}
