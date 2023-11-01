package com.example.task_manager.entity.log;


import com.example.task_manager.entity.event.Event;
import com.example.task_manager.entity.token.TokenRepository;
import com.example.task_manager.entity.user.User;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    private final LogRepository logRepository;
    private final TokenRepository tokenRepository;

    public LogService(LogRepository logRepository, TokenRepository tokenRepository) {
        this.logRepository = logRepository;
        this.tokenRepository = tokenRepository;
    }

    public void selectOperation(char selector, Object object, String generatedToken) {
        String moderatorEmail = tokenRepository.findTokenByGeneratedToken(generatedToken).getEmail();
        if (object.getClass() == User.class) {
            User user = (User) object;
            switch (selector) {
                case 'a' -> addedNewItemUser(user, moderatorEmail);
                case 'c' -> changedItemUser(user, moderatorEmail);
                case 'd' -> deletedItemUser(user, moderatorEmail);
            }
        } else if (object.getClass() == Event.class) {
            Event event = (Event) object;
            switch (selector) {
                case 'a' -> addedNewItemEvent(event, moderatorEmail);
                case 'c' -> changedItemEvent(event, moderatorEmail);
                case 'd' -> deletedItemEvent(event, moderatorEmail);
            }
        }

    }

    private void addedNewItemUser(User user, String moderatorEmail) {

    }

    private void changedItemUser(User user, String moderatorEmail) {

    }

    private void deletedItemUser(User user, String moderatorEmail) {

    }

    private void addedNewItemEvent(Event event, String moderatorEmail) {

    }

    private void changedItemEvent(Event event, String moderatorEmail) {

    }

    private void deletedItemEvent(Event event, String moderatorEmail) {

    }
}
