package com.example.task_manager.security.controller;

import com.example.task_manager.security.entity.LoginUser;
import com.example.task_manager.security.service.SecurityService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/s/")
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
}
