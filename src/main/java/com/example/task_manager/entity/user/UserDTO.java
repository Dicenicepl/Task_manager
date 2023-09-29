package com.example.task_manager.entity.user;

import com.example.task_manager.entity.role.Role;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private String username;
    private String email;
    private String password;

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
