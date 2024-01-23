package com.example.task_manager.security.controller;

import com.example.task_manager.security.entity.LoginUser;
import com.example.task_manager.security.service.SecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/s/")
@CrossOrigin(origins = "http://localhost:8080")
public class SecurityController {
    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginPage(@RequestBody LoginUser loginUser){
        return securityService.loginUser(loginUser);
    }


//    @PostMapping("/register")
//    public ResponseEntity<String> registerPage(@RequestBody RegisterUser registerUser){
//        return securityService.saveUser(registerUser);
//    }
}
