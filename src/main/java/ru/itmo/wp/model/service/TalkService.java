package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public List<TalkView> findByUser(User user) {
        List<Talk> talks = talkRepository.findByUser(user);
        List<TalkView> talkViews = new ArrayList<>();
        for (Talk talk : talks) {
            TalkView talkView = new TalkView();
            talkView.setTalk(talk);
            talkView.setSourceUser(userRepository.find(talk.getSourceUserId()));
            talkView.setTargetUser(userRepository.find(talk.getTargetUserId()));
            talkViews.add(talkView);
        }
        return talkViews;
    }

    public void sendTalk(Talk talk) {
        talkRepository.save(talk);
    }

    public static final class TalkView {
        private Talk talk;
        private User sourceUser;
        private User targetUser;

        public Talk getTalk() {
            return talk;
        }

        public void setTalk(Talk talk) {
            this.talk = talk;
        }

        public User getSourceUser() {
            return sourceUser;
        }

        public void setSourceUser(User sourceUser) {
            this.sourceUser = sourceUser;
        }

        public User getTargetUser() {
            return targetUser;
        }

        public void setTargetUser(User targetUser) {
            this.targetUser = targetUser;
        }
    }
}
