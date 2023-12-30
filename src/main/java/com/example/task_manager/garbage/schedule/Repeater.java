package com.example.task_manager.garbage.schedule;

import com.example.task_manager.garbage.entity.token.TokenRepository;
import com.example.task_manager.garbage.service.MailSender;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class Repeater {

    private final TokenRepository tokenRepository;


    public Repeater(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

//    @Scheduled(fixedDelay = 100000L)
    private void activeTimerTasks(){
    sendEmails();
    }
    public void sendEmails(){
        new MailSender();
    }
    
}