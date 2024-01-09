package com.example.task_manager.tokens.entities;

import jakarta.persistence.*;
import lombok.*;

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
    private Long timeInMinis = System.currentTimeMillis() + 600000L;
    public Token(String token, String assignedEmail) {
        this.token = token;
        this.assignedEmail = assignedEmail;
    }


}
