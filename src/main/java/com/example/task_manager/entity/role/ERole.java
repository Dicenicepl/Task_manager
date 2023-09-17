package com.example.task_manager.entity.role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class ERole {
    @Id
    @GeneratedValue
    private Integer id_role;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    public ERole(String email, String role) {
        this.email = email;
        this.role = switch (role){
            case "USER" -> Role.USER;
            case "ADMIN" -> Role.ADMIN;
            default -> throw new IllegalStateException("Unexpected value: " + role);
        };
    }
}
