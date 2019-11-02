package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/** @noinspection unused*/
public class Page {
    private final UserService userService = new UserService();
    protected HttpServletRequest lastRequest = null;

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        lastRequest = request;
        putUser(view);
        putMessage(view);
        view.put("userCount", userService.findCount());
    }

    protected void after() {

    }

    protected void action() {

    }

    private void putUser(Map<String, Object> view) {
        User user = getUser();
        if (user != null) {
            view.put("user", user);
        }
    }

    private void putMessage(Map<String, Object> view) {
        String message = (String) lastRequest.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            lastRequest.getSession().removeAttribute("message");
        }
    }

    public void setMessage(String message) {
        lastRequest.getSession().setAttribute("message", message);
    }

    public void setUser(User user) {
        lastRequest.getSession().setAttribute("user", user);
    }

    public User getUser() {
        return (User) lastRequest.getSession().getAttribute("user");
    }

    public void removeUser() {
        lastRequest.getSession().removeAttribute("user");
    }
}
