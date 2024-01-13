package com.example.task_manager.token;

import com.example.task_manager.tokens.services.TokenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TokenServiceTest {

    @Autowired
    TokenService tokenService;

    String token;
    @BeforeEach
    void preparing(){
        token = tokenService.saveUserToken("test123@example.com");
    }
    @AfterEach
    void clearing(){
        tokenService.deleteToken(token);
    }

    @Test
    @DisplayName("Is not expired")
    void isNotExpired(){
        assertTrue(tokenService.isNotExpired(token));
    }

}
