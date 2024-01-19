package com.example.task_manager.roles.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private RoleList role;

    public Role(String email, RoleList role) {
        this.email = email;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role_id=" + role_id +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
