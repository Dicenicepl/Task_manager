package com.example.task_manager;

import com.example.task_manager.users.entities.DeleteUserData;
import com.example.task_manager.users.entities.RegisterUserData;
import com.example.task_manager.users.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MainTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Get all users")
    void getAllUsersTest(){
        assertTrue(userService.getAllUsers().getBody() == null);
    }

    @Test
    @DisplayName("Get user by email")
    void getUserByEmail(){
        userService.getUserByEmail("fdgffghj");
        userService.getUserByEmail(null);
    }

    @Test
    @DisplayName("Create user")
    void createUser(){
        //Preparing for tests
        try {
            userService.deleteUser(new DeleteUserData("Email2", "password"));
        }catch (NullPointerException ignored){}
        //
        assertTrue(userService.createUser(null).getStatusCode().is4xxClientError());

        assertTrue(userService.createUser(new RegisterUserData(null, null, null)).getStatusCode().is4xxClientError());

        assertTrue(userService.createUser(new RegisterUserData("Name", null, null)).getStatusCode().is4xxClientError());

        assertTrue(userService.createUser(new RegisterUserData("Name", "Email", null)).getStatusCode().is4xxClientError());

        assertTrue(userService.createUser(new RegisterUserData("Name", "Email2", "password")).getStatusCode().is2xxSuccessful());

        assertTrue(userService.createUser(new RegisterUserData("Name", "Email2", "password")).getStatusCode().is4xxClientError());
    }
}

