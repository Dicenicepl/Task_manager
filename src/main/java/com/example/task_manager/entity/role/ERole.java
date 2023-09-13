package com.example.task_manager.entity.role;

import com.example.task_manager.entity.user.User;
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
    private String user_email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @JoinColumn(name = "user_email")
    private User user;
}
