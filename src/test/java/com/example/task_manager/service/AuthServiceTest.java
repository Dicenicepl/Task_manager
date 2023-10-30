package com.example.task_manager.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AuthServiceTest {
    @Autowired
    AuthService authService;
    public final String USER_TOKEN = "AAAAAAAAAA";
    @Test
    @DisplayName("Success")
    public void test(){
        Map<String, String> json = new HashMap<>();
        json.put("token", USER_TOKEN);
        json.put("name", "project");
        json.put("description", "input here");
        assertEquals(new ResponseEntity<>("Event has been saved", HttpStatus.OK), authService.saveEvent(json));
        authService.deleteEvent("project", USER_TOKEN);
    }
    @Test
    @DisplayName("Null Catch")
    public void test1(){
        Map<String, String> json = new HashMap<>();
        json.put("token", "");
        json.put("name", "project");
        json.put("description", "input here");
        assertEquals(new ResponseEntity<>("Check user token", HttpStatus.OK), authService.saveEvent(json));
        authService.deleteEvent("project", USER_TOKEN);
    }
    @Test
    @DisplayName("Null name")
    public void test2(){
        Map<String, String> json = new HashMap<>();
        json.put("token", USER_TOKEN);
        json.put("name", null);
        json.put("description", "input here");
        assertEquals(new ResponseEntity<>("Retry put name", HttpStatus.BAD_REQUEST), authService.saveEvent(json));
        authService.deleteEvent("project", USER_TOKEN);
    }

}