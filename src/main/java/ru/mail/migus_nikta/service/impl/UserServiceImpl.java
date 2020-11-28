package ru.mail.migus_nikta.service.impl;

import ru.mail.migus_nikta.annotation.Inject;
import ru.mail.migus_nikta.dao.EventDao;
import ru.mail.migus_nikta.dao.UserDao;
import ru.mail.migus_nikta.service.UserService;

public class UserServiceImpl implements UserService {

    @Inject
    public UserServiceImpl(UserDao dao) {

    }

    @Inject
    public UserServiceImpl(UserDao dao, EventDao eventDao) {

    }

}
