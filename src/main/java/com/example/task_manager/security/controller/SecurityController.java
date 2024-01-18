package com.example.task_manager.security.controller;

import com.example.task_manager.security.entity.LoginUser;
import com.example.task_manager.security.entity.RegisterUser;
import com.example.task_manager.security.service.SecurityService;
import org.springframework.http.HttpHeaders;
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
        if (securityService.checkUserDataToLogin(loginUser)) {
            String token = securityService.generateTokenAndSave(loginUser.getEmail());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", token);
            return ResponseEntity.ok().headers(responseHeaders).body("good");
        }
        return ResponseEntity.badRequest().body("bad");
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerPage(@RequestBody RegisterUser registerUser){
        if (securityService.checkUserDataToRegister(registerUser)){
            securityService.saveUser(registerUser);
            return ResponseEntity.ok("Saved");
        }
        return ResponseEntity.badRequest().body("Bad data");
    }
}
