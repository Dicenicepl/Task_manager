package com.example.task_manager.security.configuration;

import com.example.task_manager.tokens.services.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClearAutorizationTest {

    @Autowired
    private ClearAutorization clearAutorization;
    @Autowired
    private TokenService tokenService;

    @BeforeEach
    void preparing(){
        for (int i = 0; i < 100; i++) {
            tokenService.saveUserToken("Testico" + i);
        }
    }
    @Test
    void clearTokens() {
        clearAutorization.clearTokens();
    }
}