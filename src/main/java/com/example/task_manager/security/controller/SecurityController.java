package com.example.task_manager.security.controller;

import com.example.task_manager.security.entity.LoginUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @PostMapping("/login")
    public AuthenticationResponse loginPage(@RequestBody LoginUser loginUser){
        return new AuthenticationResponse("token");
    }
}
