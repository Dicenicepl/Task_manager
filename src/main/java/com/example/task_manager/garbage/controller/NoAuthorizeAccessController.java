package com.example.task_manager.garbage.controller;

import com.example.task_manager.garbage.entity.user.UserDTO;
import com.example.task_manager.garbage.service.NoAuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class NoAuthorizeAccessController {
    private final NoAuthService noAuthService;

    public NoAuthorizeAccessController(NoAuthService noAuthService) {
        this.noAuthService = noAuthService;
    }

    @GetMapping(value = "/user/get/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getByEmail(String email) {
        return noAuthService.getByEmail(email);
    }

    @GetMapping("/user/get/all")
    public ResponseEntity<String> getAll() {
        return noAuthService.getAll();
    }

    @PostMapping("/user/register/")
    public ResponseEntity<String> register(@RequestBody UserDTO user) {
        return noAuthService.register(user);
    }

    @GetMapping("/event/get/")
    public ResponseEntity<String> getEventByName(String name) {
        return noAuthService.getEventByName(name);
    }

}
