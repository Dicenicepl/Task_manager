package com.example.task_manager.service;


import com.example.task_manager.entity.event.Event;
import com.example.task_manager.entity.log.LogClass;
import com.example.task_manager.entity.log.LogRepository;
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
    private String getEmailByToken(String generatedToken){
        if (!generatedToken.isBlank()) {
            return tokenRepository.findTokenByGeneratedToken(generatedToken).getEmail();
        }
        return null;
    }

    // TODO: 03.11.2023 add for change catcher what was changed
    // TODO: 03.11.2023 change architecture of project, logger must be first in step cause of catching data from database
    public void selectOperation(char selector, Object object, String generatedToken) {
        String moderatorEmail = getEmailByToken(generatedToken);
        if (object.getClass() == User.class) {
            User user = (User) object;
            switch (selector) {
                case 'a' -> AddUserToProjectGroup(user, moderatorEmail);
                case 'c' -> changePermissionForUser(user, moderatorEmail);
                case 'd' -> deleteUserFromProject(user, moderatorEmail);
            }
        } else if (object.getClass() == Event.class) {
            Event event = (Event) object;
            switch (selector) {
                case 'a' -> addTaskToProject(event, moderatorEmail);
                case 'c' -> modifyTask(event, moderatorEmail);
                case 'd' -> deleteTaskFromProject(event, moderatorEmail);
            }
        }

    }

    private void AddUserToProjectGroup(User user, String moderatorEmail) {
        LogClass logClass = new LogClass(user.getUsername() + " is added to project group by: ", moderatorEmail);
        logRepository.save(logClass);
    }

    private void changePermissionForUser(User user, String moderatorEmail) {
        LogClass positive = new LogClass(user.getUsername() + " get promoted to: GABEN", moderatorEmail);
        LogClass negative = new LogClass(user.getUsername() + " get demoted to: GABEN", moderatorEmail);
        logRepository.save(positive);
        logRepository.save(negative);
    }

    private void deleteUserFromProject(User user, String moderatorEmail) {
        LogClass logClass = new LogClass(user.getUsername() + " is deleted from project by: ", moderatorEmail);
        logRepository.save(logClass);
    }

    private void addTaskToProject(Event event, String moderatorEmail) {
        LogClass logClass = new LogClass(event.getName() + " task, is added by: ", moderatorEmail);
        logRepository.save(logClass);
    }

    private void modifyTask(Event event, String moderatorEmail) {
        LogClass logClass = new LogClass(event.getName() + " is updated by: ", moderatorEmail);
        logRepository.save(logClass);
    }

    private void deleteTaskFromProject(Event event, String moderatorEmail) {
        LogClass logClass = new LogClass(event.getName() + " is deleted from project by: ", moderatorEmail);
        logRepository.save(logClass);
    }
}