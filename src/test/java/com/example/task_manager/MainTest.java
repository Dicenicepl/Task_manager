package com.example.task_manager;

import com.example.task_manager.users.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MainTest {

    @Autowired
    private UserService userService;

//    @Test
//    @DisplayName("Get all users")
//    void getAllUsersTest(){
//        assertNull(userService.getAllUsers().getBody());
//    }

    @Test
    @DisplayName("Get user by email")
    void getUserByEmail(){
        userService.getUserByEmail("fdgffghj");
        userService.getUserByEmail(null);
    }

//    @Test
//    @DisplayName("Create user")
//    void createUser(){
//        //Preparing for tests
//        try {
//            userService.deleteUser(new DeleteUserDTO("Email2", "password"));
//        }catch (NullPointerException ignored){}
//        //
//        assertTrue(userService.createUser(null).getStatusCode().is4xxClientError());
//
//        assertTrue(userService.createUser(new User(null, null, null)).getStatusCode().is4xxClientError());
//
//        assertTrue(userService.createUser(new User("Name", null, null)).getStatusCode().is4xxClientError());
//
//        assertTrue(userService.createUser(new User("Name", "Email", null)).getStatusCode().is4xxClientError());
//
//        assertTrue(userService.createUser(new User("Name", "Email2", "password")).getStatusCode().is2xxSuccessful());
//
//        assertTrue(userService.createUser(new User("Name", "Email2", "password")).getStatusCode().is4xxClientError());
//    }
}

