package com.example.task_manager.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
            name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long user_id;
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
//    @ManyToOne
//    @JoinColumn(name = "event_id", nullable = false)
//    private Event event;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


}
