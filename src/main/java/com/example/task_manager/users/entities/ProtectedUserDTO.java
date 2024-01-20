package com.example.task_manager.users.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProtectedUserDTO {
    private String username;
    private String email;

}
