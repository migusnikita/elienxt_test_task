package ru.mail.migus_nikta.service.impl;

import ru.mail.migus_nikta.service.Provider;

public class ProviderImpl<T> implements Provider<T> {

    private final T obj;

    public ProviderImpl(T obj) {
        this.obj = obj;
    }

    public T getInstance() {
        return obj;
    }

}
