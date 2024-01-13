package com.example.task_manager.security.configuration;

import com.example.task_manager.tokens.entities.Token;
import com.example.task_manager.tokens.services.TokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class ClearAutorization {
    private final TokenService tokenService;

    public ClearAutorization(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    //todo repair OutOfIndexException when this.timeinminis == other.timeinminis
    @Scheduled(fixedDelay = 60000L)
    public void clearTokens() {
        List<Token> tokenList = tokenService.findAllTokens();
        for (Token token : tokenList) {
            if (!tokenService.isNotExpired(token.getToken())) {
                tokenService.deleteTokenById(token.getToken_id());
            }
        }
    }

    public void createXTokens(){
        for (int i = 0; i< 10; i++){
            tokenService.saveUserToken("test"+i);
        }
    }

}
