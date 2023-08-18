package com.example.task_manager.controller;

import com.example.task_manager.entity.user.User;
import com.example.task_manager.service.NoAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class NoAuthorizeAccessController {
    private final NoAuthService noAuthService;

    public NoAuthorizeAccessController(NoAuthService noAuthService) {
        this.noAuthService = noAuthService;
    }

    @PostMapping ("/user/register")
    public ResponseEntity<String> register(@RequestBody User user){
        return noAuthService.register(user);
    }

    @GetMapping("/user/get/")
    public ResponseEntity<String> getById(Long id){
        return noAuthService.getById(id);
    }

    @GetMapping("/user/get/all")
    public ResponseEntity<String> getAll(){
        return noAuthService.getAll();
    }
}
