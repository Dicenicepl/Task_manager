package com.example.task_manager.logs.entities;

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
@Table(name = "Logs")
public class Log {
    @Id
    @SequenceGenerator(
            name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long log_id;
    private Date dataOfUpdate = new Date(System.currentTimeMillis());
    private Time timeOfUpdate = new Time(System.currentTimeMillis());
    private String changes;
    private String whoUpdated;

    public Log(String changes, String whoUpdated) {
        this.changes = changes;
        this.whoUpdated = whoUpdated;
    }
}
