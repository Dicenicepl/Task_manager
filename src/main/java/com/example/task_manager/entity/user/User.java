package com.example.task_manager.entity.user;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Optional;

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
    private String token;
    private Time expireTime = new Time(System.currentTimeMillis() + 10000L);

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


}
