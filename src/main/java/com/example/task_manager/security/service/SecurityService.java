package com.example.task_manager.security.service;

import com.example.task_manager.security.entity.LoginUser;
import com.example.task_manager.tokens.services.TokenService;
import com.example.task_manager.users.entities.User;
import com.example.task_manager.users.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserService userService;
    private final TokenService tokenService;

    public SecurityService(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }
    private boolean checkUserDataToLogin(LoginUser loginUser) {
        if (loginUser == null) {
            return false;
        }
        User user = userService.getUserFromEmail(loginUser.getEmail());
        if (user == null) {
            return false;
        }
        String password = user.getPassword();
        return passwordEncoder.matches(loginUser.getPassword(), password);
    }

    public ResponseEntity<String> loginUser(LoginUser loginUser) {
        if (checkUserDataToLogin(loginUser)) {
            String token = tokenService.saveUserToken(loginUser.getEmail());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", token);
            return ResponseEntity.ok().headers(httpHeaders).body("Login success");
        }
        return ResponseEntity.badRequest().body("Invalid email or password");
    }

}
