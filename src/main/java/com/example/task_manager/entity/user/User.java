package com.example.task_manager.entity.user;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String username;
    @Column(unique = true)
    String email;
    String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    String token;
    Time expireToken = new Time(System.currentTimeMillis() + 10000L);

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
