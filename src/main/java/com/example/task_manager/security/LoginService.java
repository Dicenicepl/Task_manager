package com.example.task_manager.security;

import com.example.task_manager.user.User;
import com.example.task_manager.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user.getId() + "_" + generatorToken(user.getId());
        }
        return "Bad Request";
    }

    private String generatorToken(Long id) {
        String[] array = new String[]{"A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
                "T", "U", "V", "W", "X", "Y", "Z"};
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            token.append(array[random.nextInt(array.length)]);
        }
        userRepository.addToken(id,String.valueOf(token));
        return token.toString();
    }
}
