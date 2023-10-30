package com.example.task_manager.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class AuthorizeServiceTest {
    @Autowired
    AuthService authService;

    private static final Map<String, String> userMethodsJson = new HashMap<>();
    private static final Map<String, String> eventMethodsJson = new HashMap<>();

    @BeforeAll
    public static void preparing(){

        userMethodsJson.put("email","Dicenicepl@gmail.com");
        userMethodsJson.put("password", "1234");
        userMethodsJson.put("username", "Dicenice");
        userMethodsJson.put("generatedToken", "AAAAAAAAAA");

        eventMethodsJson.put("generatedToken","AAAAAAAAAA");
        eventMethodsJson.put("owner_email","dicenicepl@gmail.com");
        eventMethodsJson.put("name","Project");
        eventMethodsJson.put("description","Koty to sa super lecz psy beda the best");
    }

    @Test
    @DisplayName("Login test")
    public void loginTest(){
        assertTrue(authService.login(userMethodsJson).getStatusCode().isSameCodeAs(HttpStatus.OK));
    }
}