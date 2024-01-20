package com.example.task_manager;

import com.example.task_manager.users.entities.DeleteUserDTO;
import com.example.task_manager.users.entities.ProtectedUserDTO;
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
        DeleteUserDTO deleteUserDTO = new DeleteUserDTO(
                "test@example.com",
                "zaq1@WSX"
        );
        userService.deleteUser(deleteUserDTO);
    }
    @Test
    @DisplayName("Get all users")
    void getAllUsers(){
        assertEquals(HttpStatus.OK, userService.getAllUsers().getStatusCode());
    }


    @Test
    @DisplayName("Get user by email")
    void getUserByEmail(){
        ResponseEntity<ProtectedUserDTO> userResponse = userService.getUserByEmail("test@example.com");
        assertEquals(HttpStatus.OK, userResponse.getStatusCode());
        assertEquals("test@example.com", userResponse.getBody().getEmail());
    }
}
