package com.example.task_manager.schedule;

import com.example.task_manager.entity.token.Token;
import com.example.task_manager.entity.token.TokenRepository;
import com.example.task_manager.service.MailSender;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Time;
import java.util.List;

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