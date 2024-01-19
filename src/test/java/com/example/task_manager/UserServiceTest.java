package com.example.task_manager;

import com.example.task_manager.users.entities.DeleteUserData;
import com.example.task_manager.users.entities.ProtectedUserData;
import com.example.task_manager.users.entities.User;
import com.example.task_manager.users.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @BeforeEach
    void preparing(){
        User user = new User(
                "test",
                "testasdas@example.com",
                "zaq1@WSX"
        );
        userService.createUser(user);
    }
    @AfterEach
    void clearing(){
        DeleteUserData deleteUserData = new DeleteUserData(
                "test@example.com",
                "zaq1@WSX"
        );
        userService.deleteUser(deleteUserData);
    }
    @Test
    @DisplayName("Get all users")
    void getAllUsers(){
        assertEquals(HttpStatus.OK, userService.getAllUsers().getStatusCode());
    }


    @Test
    @DisplayName("Get user by email")
    void getUserByEmail(){
        ResponseEntity<ProtectedUserData> userResponse = userService.getUserByEmail("test@example.com");
        assertEquals(HttpStatus.OK, userResponse.getStatusCode());
        assertEquals("test@example.com", userResponse.getBody().getEmail());
    }
}
