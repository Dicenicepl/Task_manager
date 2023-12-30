package com.example.task_manager.tokens.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @SequenceGenerator(
            name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long token_id;
    @Column(unique = true)
    private String token;
    @Column(unique = true)
    private String assignedEmail;
    private Date expireDate;
    private Time expireTime;

    public Token(String token, String assignedEmail, Date expireDate, Time expireTime) {
        this.token = token;
        this.assignedEmail = assignedEmail;
        this.expireDate = expireDate;
        this.expireTime = expireTime;
    }
}
