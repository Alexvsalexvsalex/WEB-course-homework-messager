package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.EventListener;
import java.util.Map;

/** @noinspection unused*/
public class EnterPage extends Page {
    private final UserService userService = new UserService();
    private final EventService eventService = new EventService();

    private void enter(HttpServletRequest request) throws ValidationException {
        String loginOrEmail = request.getParameter("loginOrEmail");
        String password = request.getParameter("password");

        User user = userService.validateAndFindByLoginOrEmailAndPassword(loginOrEmail, password);
        setUser(user);
        setMessage("Hello, " + user.getLogin());

        eventService.addEnterEvent(getUser());

        throw new RedirectException("/index");
    }
}
