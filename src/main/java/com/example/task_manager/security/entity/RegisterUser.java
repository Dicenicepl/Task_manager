package com.example.task_manager.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUser {
    private String username;
    private String email;
    private String password;
}
