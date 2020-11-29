package ru.mail.migus_nikta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import ru.mail.migus_nikta.dao.SecondTestDao;
import ru.mail.migus_nikta.dao.TestDao;
import ru.mail.migus_nikta.dao.impl.SecondTestDaoImpl;
import ru.mail.migus_nikta.dao.impl.TestDaoImpl;
import ru.mail.migus_nikta.exception.BindingNotFoundException;
import ru.mail.migus_nikta.exception.ConstructorNotFoundException;
import ru.mail.migus_nikta.exception.TooManyConstructorsException;
import ru.mail.migus_nikta.service.EventService;
import ru.mail.migus_nikta.service.Injector;
import ru.mail.migus_nikta.service.Provider;
import ru.mail.migus_nikta.service.UserService;
import ru.mail.migus_nikta.service.impl.EventServiceImpl;
import ru.mail.migus_nikta.service.impl.InjectorImpl;
import ru.mail.migus_nikta.service.impl.UserServiceImpl;

public class AppTest {

    @Test
    public void testExistingBinding() {
        Injector injector = new InjectorImpl();
        injector.bind(SecondTestDao.class, SecondTestDaoImpl.class);
        Provider<SecondTestDao> serviceProvider = injector.getProvider(SecondTestDao.class);
        assertNotNull(serviceProvider);
        assertNotNull(serviceProvider.getInstance());
        assertSame(SecondTestDaoImpl.class, serviceProvider.getInstance().getClass());
    }

    @Test
    public void testComplicatedBinding() {
        Injector injector = new InjectorImpl();
        injector.bind(EventService.class, EventServiceImpl.class);
        injector.bind(SecondTestDao.class, SecondTestDaoImpl.class);
        Provider<EventService> serviceProvider = injector.getProvider(EventService.class);
        assertNotNull(serviceProvider);
        assertNotNull(serviceProvider.getInstance());
        assertSame(EventServiceImpl.class, serviceProvider.getInstance().getClass());
    }

    @Test
    public void testNullBinding() {
        Injector injector = new InjectorImpl();
        Provider<EventService> serviceProvider = injector.getProvider(EventService.class);
        assertNull(serviceProvider);
    }

    @Test
    public void testSingletonBinding() {
        Injector injector = new InjectorImpl();
        injector.bindSingleton(EventService.class, EventServiceImpl.class);
        injector.bind(SecondTestDao.class, SecondTestDaoImpl.class);
        Provider<EventService> serviceProvider = injector.getProvider(EventService.class);
        serviceProvider.getInstance().setOperationsCount(2);
        Provider<EventService> serviceProvider2 = injector.getProvider(EventService.class);
        assertEquals(serviceProvider2.getInstance().getOperationsCount(), 2);
    }

    @Test(expected = BindingNotFoundException.class)
    public void testBindingNotFoundExceptionInBinding() {
        Injector injector = new InjectorImpl();
        injector.bind(EventService.class, EventServiceImpl.class);
        injector.getProvider(EventService.class);
    }

    @Test(expected = TooManyConstructorsException.class)
    public void testTooManyConstructorsExceptionInBinding() {
        Injector injector = new InjectorImpl();
        injector.bind(UserService.class, UserServiceImpl.class);
        injector.getProvider(EventService.class);
    }

    @Test(expected = ConstructorNotFoundException.class)
    public void testConstructorNotFoundExceptionInBinding() {
        Injector injector = new InjectorImpl();
        injector.bind(TestDao.class, TestDaoImpl.class);
        injector.getProvider(TestDao.class);
    }

}
