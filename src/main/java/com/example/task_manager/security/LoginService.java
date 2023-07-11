package com.example.task_manager.security;

import com.example.task_manager.user.User;
import com.example.task_manager.user.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(String email, String password) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get().getId() + "_" + generatorToken(user.get().getId());
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

    //TODO wytwarzanie błędu opisujące konkretnie co nie działa
    public String register(String username, String email, String password) {
        if (userRepository.findUserByEmail(email).isEmpty()){
            userRepository.save(new User(username,email,password));
            return "User registered";
        }
        return "Something went wrong";
    }
}
