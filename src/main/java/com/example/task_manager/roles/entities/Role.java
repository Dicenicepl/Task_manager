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
    @Enumerated(EnumType.STRING)
    private RoleList role;

    public Role(String email, RoleList role) {
        this.email = email;
        this.role = role;
    }
}
