package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @noinspection unused
 */
public class TalksPage extends Page {
    private final UserService userService = new UserService();
    private final TalkService talkService = new TalkService();

    private void action(Map<String, Object> view) {
        User currentUser = getUser();
        if (currentUser == null) {
            setMessage("Please, login to get access to chat");
            throw new RedirectException("/index");
        }
        view.put("users", userService.findAll());
        view.put("talkViews", talkService.findByUser(currentUser));
    }

    private void sendTalk(HttpServletRequest request) {
        User currentUser = getUser();
        long targetUserId = Long.parseLong(request.getParameter("targetUserId"));
        String text = request.getParameter("text");
        Talk talk = new Talk();

        talk.setSourceUserId(currentUser.getId());
        talk.setTargetUserId(targetUserId);
        talk.setText(text);

        talkService.sendTalk(talk);

        throw new RedirectException("/talks");
    }


}
