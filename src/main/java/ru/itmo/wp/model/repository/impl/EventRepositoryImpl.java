package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;

import java.sql.*;

public class EventRepositoryImpl extends BasicRepositoryImpl<Event> implements EventRepository {
    private static final EventTransformer EVENT_TRANSFORMER = new EventTransformer();

    @Override
    public Event find(long id) {
        return getSingle(findBy("SELECT * FROM `Event` WHERE id=?", EVENT_TRANSFORMER, id));
    }

    @Override
    public void save(Event event) {
        saveObject("INSERT INTO `Event` (`userId`, `type`) VALUES (?, ?)", event.getUserId(), event.getType().name());
    }

    private static final class EventTransformer implements ObjectTransformer<Event> {
        @Override
        public Event toObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
            if (!resultSet.next()) {
                return null;
            }
            Event event = new Event();
            event.setId(resultSet.getLong("id"));
            event.setUserId(resultSet.getLong("userId"));
            event.setType(Event.TYPE.valueOf(resultSet.getString("type")));
            event.setCreationTime(resultSet.getTimestamp("creationTime"));
            return event;
        }
    }
}
