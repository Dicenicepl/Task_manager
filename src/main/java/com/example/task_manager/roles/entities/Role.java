package com.example.task_manager.roles.entities;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Roles")
public class Role {
    @Id
    @SequenceGenerator(
            name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long role_id;
    @Column(unique = true)
    private String email;
    private String role;

    public Role(String email, RoleList role) {
        this.email = email;
        switch (role){
            case ADMIN -> this.role = "ADMIN";
            case USER -> this.role = "USER";
            case GUEST -> this.role = "GUEST";
        }
    }
}
