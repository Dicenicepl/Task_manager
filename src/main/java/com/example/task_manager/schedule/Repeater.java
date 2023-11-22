package com.example.task_manager.schedule;

import com.example.task_manager.entity.event.EventRepository;
import com.example.task_manager.entity.token.Token;
import com.example.task_manager.entity.token.TokenRepository;
import com.example.task_manager.service.MailSender;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.security.GeneralSecurityException;
import java.sql.Time;
import java.util.List;

@Configuration
@EnableScheduling
public class Repeater {

    private final TokenRepository tokenRepository;


    public Repeater(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(fixedDelay = 1000L)
    void activeTimerTasks() throws GeneralSecurityException {
    sendEmails();
    checkToken();
    }
    public void sendEmails() throws GeneralSecurityException {

        new MailSender();
    }
    public void checkToken() {
        List<Token> tokens = tokenRepository.findAllByExpireTimeBefore(new Time(System.currentTimeMillis()));
        try {
            for (Token token : tokens) {
                if (!token.getGeneratedToken().equals("AAAAAAAAAA")) {
                    tokenRepository.updateTokenToNull(token.getGeneratedToken());
                }
            }
        } catch (NullPointerException e) {
            System.out.println("CHECKEXPIRETOKEN: NULL");
        }
    }
}