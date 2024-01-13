package com.example.task_manager.security;

import com.example.task_manager.security.entity.LoginUser;
import com.example.task_manager.security.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SecurityServiceTest {

    @Autowired
    SecurityService securityService;

    @Test
    void checkUserDataToLogin() {
        assertTrue(securityService.checkUserDataToLogin(new LoginUser("test1@example.com", "test")));
    }
}