package com.example.task_manager.entity;


import com.example.task_manager.role.AppRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Users")
public class AppUser {
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "First_name")
    private String firstName;
    @Column(name = "Last_name")
    private String lastName;
    @Column(name = "Nickname")
    private String nickName;
    @Column(name = "Email")
    private String email;
    @Column(name = "Password")
    private String password;
    @Column(name = "Role")
    private String role;

    public AppUser(String firstName, String lastName, String nickName, String email, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
