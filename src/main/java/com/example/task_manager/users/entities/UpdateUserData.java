package com.example.task_manager.users.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserData {
    private String email;
    private String username;
    private String password;
}
