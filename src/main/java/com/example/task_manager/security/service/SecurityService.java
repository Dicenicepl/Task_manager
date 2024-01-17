package com.example.task_manager.security.service;

import com.example.task_manager.security.entity.LoginUser;
import com.example.task_manager.security.entity.RegisterUser;
import com.example.task_manager.tokens.services.TokenService;
import com.example.task_manager.users.entities.User;
import com.example.task_manager.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public SecurityService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public boolean checkUserDataToLogin(LoginUser loginUser) {
        if (loginUser == null){
            return false;
        }
        User user = userRepository.findUserByEmail(loginUser.getEmail());
        if (user == null){
            return false;
        }
        return user.getEmail().equals(loginUser.getEmail()) && user.getPassword().equals(loginUser.getPassword());
    }

    public String generateTokenAndSave(String email){
        return tokenService.saveUserToken(email);
    }

    public boolean checkUserDataToRegister(RegisterUser registerUser){
        if (registerUser == null){
            return false;
        }
        if (registerUser.getUsername() == null || registerUser.getEmail() == null || registerUser.getPassword() == null){
            return false;
        }
        return userRepository.findUserByEmail(registerUser.getEmail()) != null;
    }
    public void saveUser(RegisterUser registerUser){
        User user = new User(registerUser.getUsername(), registerUser.getEmail(), registerUser.getPassword());
        userRepository.save(user);
    }
}
