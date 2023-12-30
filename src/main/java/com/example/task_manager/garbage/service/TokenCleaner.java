package com.example.task_manager.garbage.service;

import java.sql.Time;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.task_manager.garbage.entity.token.Token;
import com.example.task_manager.garbage.entity.token.TokenRepository;

@Service
@EnableScheduling
public class TokenCleaner {

    private final TokenRepository tokenRepository;

    public TokenCleaner(TokenRepository tokenRepository){
        this.tokenRepository = tokenRepository;
    }
    private List<Token> getAllExpiredToken(){
        return tokenRepository.findAllExpireTime(new Time(System.currentTimeMillis()));
    }
    @Scheduled(fixedDelay = 100000l)
    public void activeDeleter(){
        System.out.println("ACTIVE DELETER: ACTIVATED");
        for (Token token : getAllExpiredToken()) {
            tokenRepository.deleteExpiredToken(token.getGeneratedToken());    
        }
        
    }
}