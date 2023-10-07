package com.example.task_manager.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
class AuthServiceTest {

    @Autowired
    AuthService authService;
    @Test
    @DisplayName("Testing isExpiredToken")
    void test() {
        assertFalse(authService.isExpiredToken("AAAAAAAAAA"));
    }

}