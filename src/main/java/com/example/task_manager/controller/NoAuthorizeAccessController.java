package com.example.task_manager.controller;

import com.example.task_manager.entity.user.User;
import com.example.task_manager.service.NoAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoAuthorizeAccessController {
    private final NoAuthService noAuthService;

    public NoAuthorizeAccessController(NoAuthService noAuthService) {
        this.noAuthService = noAuthService;
    }

    @GetMapping("/register")
    public ResponseEntity<String> register(String username, String email, String password){
        return noAuthService.register(new User(username,email,password));
    }
    @GetMapping("/user/profile")
    public ResponseEntity<String> getById(Long id){
        return noAuthService.getById(id);
    }
}
