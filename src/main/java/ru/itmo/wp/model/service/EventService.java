package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;

public class EventService {
    private final EventRepository eventRepository = new EventRepositoryImpl();

    public void addEnterEvent(User user) {
        Event enterEvent = new Event();
        enterEvent.setUserId(user.getId());
        enterEvent.setType(Event.TYPE.ENTER);
        eventRepository.save(enterEvent);
    }

    public void addLogoutEvent(User user) {
        Event logoutEvent = new Event();
        logoutEvent.setUserId(user.getId());
        logoutEvent.setType(Event.TYPE.LOGOUT);
        eventRepository.save(logoutEvent);
    }
}
