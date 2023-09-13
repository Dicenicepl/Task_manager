package com.example.task_manager.entity.user;

import com.example.task_manager.entity.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToOne(mappedBy = "users")
    private Role role;

    private String token;
    private Time expireTime = new Time(System.currentTimeMillis() + 10000L);

}
