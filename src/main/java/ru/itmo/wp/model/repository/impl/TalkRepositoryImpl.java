package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.TalkRepository;

import java.sql.*;
import java.util.List;

public class TalkRepositoryImpl extends BasicRepositoryImpl<Talk> implements TalkRepository {
    private static final TalkTransformer TALK_TRANSFORMER = new TalkTransformer();

    @Override
    public Talk find(long id) {
        return getSingle(findBy("SELECT * FROM `Talk` WHERE id=?", TALK_TRANSFORMER, id));
    }

    @Override
    public List<Talk> findByUser(User user) {
        return findBy("SELECT * FROM `Talk` WHERE (sourceUserId=? OR targetUserId=?) ORDER BY creationTime DESC", TALK_TRANSFORMER, user.getId(), user.getId());
    }

    @Override
    public void save(Talk talk) {
        saveObject("INSERT INTO `Talk` (`sourceUserId`, `targetUserId`, `text`) VALUES (?, ?, ?)", talk.getSourceUserId(), talk.getTargetUserId(), talk.getText());
    }

    private static final class TalkTransformer implements ObjectTransformer<Talk> {
        @Override
        public Talk toObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
            if (!resultSet.next()) {
                return null;
            }
            Talk talk = new Talk();
            talk.setId(resultSet.getLong("id"));
            talk.setSourceUserId(resultSet.getLong("sourceUserId"));
            talk.setTargetUserId(resultSet.getLong("targetUserId"));
            talk.setText(resultSet.getString("text"));
            talk.setCreationTime(resultSet.getTimestamp("creationTime"));
            return talk;
        }
    }

}
