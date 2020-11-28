package ru.mail.migus_nikta.service.impl;

import ru.mail.migus_nikta.annotation.Inject;
import ru.mail.migus_nikta.dao.EventDao;
import ru.mail.migus_nikta.service.EventService;

public class EventServiceImpl implements EventService {

    private int operationsCount = 0;

    @Inject
    public EventServiceImpl(EventDao dao) {

    }

    public int getOperationsCount() {
        return operationsCount;
    }

    public void setOperationsCount(int operationsCount) {
        this.operationsCount = operationsCount;
    }

}
