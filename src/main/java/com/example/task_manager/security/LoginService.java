package com.example.task_manager.security;

import com.example.task_manager.entity.user.UserRepository;
import org.springframework.stereotype.Service;

//TODO zmienić nazwę LoginController i LoginService na odpowiednią dla jej funkcji
@Service
public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
