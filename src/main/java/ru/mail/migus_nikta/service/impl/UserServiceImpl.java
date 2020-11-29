package ru.mail.migus_nikta.service.impl;

import ru.mail.migus_nikta.annotation.Inject;
import ru.mail.migus_nikta.dao.SecondTestDao;
import ru.mail.migus_nikta.dao.TestDao;
import ru.mail.migus_nikta.service.UserService;

public class UserServiceImpl implements UserService {

    @Inject
    public UserServiceImpl(TestDao dao) {

    }

    @Inject
    public UserServiceImpl(TestDao dao, SecondTestDao eventDao) {

    }

}
