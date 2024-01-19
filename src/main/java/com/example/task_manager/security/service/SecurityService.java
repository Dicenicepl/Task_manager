package com.example.task_manager.security.service;

import com.example.task_manager.security.entity.LoginUser;
import com.example.task_manager.security.entity.RegisterUser;
import com.example.task_manager.tokens.services.TokenService;
import com.example.task_manager.users.entities.User;
import com.example.task_manager.users.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
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
        return user.getEmail().equals(loginUser.getEmail()) && user.getPassword().equals(loginUser.getPassword());
    }

    private boolean checkUserDataToRegister(RegisterUser registerUser) {
        if (registerUser == null) {
            return false;
        }
        if (registerUser.getUsername() == null || registerUser.getEmail() == null || registerUser.getPassword() == null) {
            return false;
        }
        return userService.getUserFromEmail(registerUser.getEmail()) == null;
    }

    private String generateTokenAndSave(String email) {
        return tokenService.saveUserToken(email);
    }

    public ResponseEntity<String> loginUser(LoginUser loginUser) {
        if (checkUserDataToLogin(loginUser)) {
            String token = generateTokenAndSave(loginUser.getEmail());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", token);
            return ResponseEntity.ok().headers(httpHeaders).body("Login success");
        }
        return ResponseEntity.badRequest().body("Invalid email or password");
    }

    public ResponseEntity<String> saveUser(RegisterUser registerUser) {
        if (checkUserDataToRegister(registerUser)) {
            User user = new User(
                    registerUser.getUsername(),
                    registerUser.getEmail(),
                    registerUser.getPassword()
            );
            userService.createUser(user);
            return ResponseEntity.ok("User register successful");
        }
        return ResponseEntity.badRequest().body("Invalid data, please check a form");

    }
}
