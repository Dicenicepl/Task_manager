package com.example.task_manager.entity.user;

import com.example.task_manager.entity.role.Role;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private String username;
    private String email;
    @OneToOne
    private Role role;
}
