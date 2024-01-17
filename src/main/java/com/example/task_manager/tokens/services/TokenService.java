package com.example.task_manager.tokens.services;

import com.example.task_manager.tokens.entities.Token;
import com.example.task_manager.tokens.repositories.TokenRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    private String tokenGenerator(){
        SecureRandom randomizer = new SecureRandom();
        StringBuilder token = new StringBuilder(10);
        for (int i = 0; i < 10; i++){
            int randomIndex = randomizer.nextInt(CHARACTERS.length());
            token.append(CHARACTERS.charAt(randomIndex));
        }
        return token.toString();
    }

    public String saveUserToken(String emailToAssignToken){
        String generatedToken = tokenGenerator();
        Token token = new Token(generatedToken,emailToAssignToken);
        if (tokenRepository.findByAssignedEmail(emailToAssignToken) == null) {
            tokenRepository.save(token);
            return generatedToken;
        }
        return null;
    }
    public boolean isNotExpired(String tokenToCheck){
        Token token = tokenRepository.findByToken(tokenToCheck);
        if (token == null){
            return false;
        }
        return token.getTimeInMinis() > System.currentTimeMillis();
    }

    @Transactional
    @Modifying
    public void deleteToken(String token){
        tokenRepository.deleteByToken(token);
    }
    public void deleteTokenById(Long id) {
        tokenRepository.deleteById(id);
    }

    public List<Token> findAllTokens(){
        return tokenRepository.findAll();
    }
    public String findAssignedEmailByToken(String token){
        Token token1 = tokenRepository.findByToken(token);
        if (token1 != null) {
            return token1.getAssignedEmail();
        }
        return null;
    }
}
