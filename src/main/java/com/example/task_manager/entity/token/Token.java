package com.example.task_manager.entity.token;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Getter
@Setter
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
    private Long id;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String generatedToken;
    private Time expireTime = new Time(System.currentTimeMillis() + 10000L);
}
