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
    @GetMapping("/user/get/")
    public ResponseEntity<String> getByEmail(String email){
        return noAuthService.getByEmail(email);
    }

    @GetMapping("/user/get/all")
    public ResponseEntity<String> getAll(){
        return noAuthService.getAll();
    }
    @PostMapping("/user/register/")
    public ResponseEntity<String> register(@RequestBody User user){
        return noAuthService.register(user);
    }
    // TODO: 29.09.2023 profile
    @GetMapping("/event/get/")
    public ResponseEntity<String> getEventByName(String name) {
        return noAuthService.getEventByName(name);
    }

}
