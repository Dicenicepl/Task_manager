package com.example.task_manager.entity.user;

import com.example.task_manager.entity.role.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private String username;
    private String email;
    private Role role;
}
