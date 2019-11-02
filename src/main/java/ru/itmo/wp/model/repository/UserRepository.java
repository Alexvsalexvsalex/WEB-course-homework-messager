package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.User;

import java.util.List;

public interface UserRepository {
    User find(long id);

    User findByEmail(String login);

    User findByLogin(String login);

    User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha);

    List<User> findAll();

    long findCount();

    void save(User user, String passwordSha);
}
