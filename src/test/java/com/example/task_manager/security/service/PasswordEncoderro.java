package com.example.task_manager.security.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PasswordEncoderro {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final String password = "password";

    String hashing(){
        return passwordEncoder.encode("password");
    }
    @Test
    void test(){
        assertTrue(passwordEncoder.matches(password, hashing()));
    }
}
