package com.example.task_manager.schedule;

import com.example.task_manager.entity.token.Token;
import com.example.task_manager.entity.token.TokenRepository;
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

    @Scheduled(fixedDelay = 60000L)
    void checkExpireToken(){
        List<Token> tokens = tokenRepository.findAllByExpireTimeBefore(new Time(System.currentTimeMillis()));
        try {
            for (Token token : tokens) {
                if (!token.getGeneratedToken().equals("AAAAAAAAAA")) {
                   tokenRepository.updateTokenToNull(token.getGeneratedToken());
                }
            }
        }catch (NullPointerException e){
            System.out.println("CHECKEXPIRETOKEN: NULL");
        }
    }
}
