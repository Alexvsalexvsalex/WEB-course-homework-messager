package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.*;
import java.util.List;

public class UserRepositoryImpl extends BasicRepositoryImpl<User> implements UserRepository {
    private static final ObjectTransformer<User> USER_TRANSFORMER = new UserTransformer();

    @Override
    public User find(long id) {
        return getSingle(findBy("SELECT * FROM `User` WHERE id=?", USER_TRANSFORMER, id));
    }

    @Override
    public User findByEmail(String email) {
        return getSingle(findBy("SELECT * FROM `User` WHERE email=?", USER_TRANSFORMER, email));
    }

    @Override
    public User findByLogin(String login) {
        return getSingle(findBy("SELECT * FROM `User` WHERE login=?", USER_TRANSFORMER, login));
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) {
        return getSingle(findBy("SELECT * FROM `User` WHERE (login=? OR email=?) AND passwordSha=?", USER_TRANSFORMER, loginOrEmail, loginOrEmail, passwordSha));
    }

    @Override
    public List<User> findAll() {
        return findBy("SELECT * FROM `User` ORDER BY id DESC", USER_TRANSFORMER);
    }

    @Override
    public long findCount() {
        return getSingle(findBy("SELECT COUNT(*) FROM User", LONG_TRANSFORMER));
    }

    @Override
    public void save(User user, String passwordSha) {
        saveObject("INSERT INTO `User` (`email`, `login`, `passwordSha`) VALUES (?, ?, ?)", user.getEmail(), user.getLogin(), passwordSha);
    }

    private static final class UserTransformer implements ObjectTransformer<User> {
        @Override
        public User toObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
            if (!resultSet.next()) {
                return null;
            }
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setEmail(resultSet.getString("email"));
            user.setLogin(resultSet.getString("login"));
            user.setCreationTime(resultSet.getTimestamp("creationTime"));
            return user;
        }
    }
}
