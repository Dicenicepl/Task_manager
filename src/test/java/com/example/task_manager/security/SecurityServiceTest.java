package com.example.task_manager.security;

import com.example.task_manager.security.entity.LoginUser;
import com.example.task_manager.security.service.SecurityService;
import com.example.task_manager.users.entities.DeleteUserDTO;
import com.example.task_manager.users.entities.User;
import com.example.task_manager.users.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SecurityServiceTest {

    @Autowired
    SecurityService securityService;

    @Autowired
    UserService userService;

    @BeforeEach
    void preparing(){
        userService.createUser(new User("Dicenice","example@example.com","1234"));
    }
    @AfterEach
    void clearing(){
        userService.deleteUser(new DeleteUserDTO("example@example.com", "1234"));
    }
    @Test
    @DisplayName("Login test")
    void loginTest(){
        securityService.loginUser(null);
        securityService.loginUser(new LoginUser(null,null));
        securityService.loginUser(new LoginUser(null,"1234"));
        securityService.loginUser(new LoginUser("example@example.com",null));
        securityService.loginUser(new LoginUser("example@example.com","1234"));
    }
}