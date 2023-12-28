package com.example.task_manager.entity.task;

import java.sql.Date;
import java.sql.Time;

import com.example.task_manager.entity.project.Project;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @SequenceGenerator(
            name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long event_id;
    private String owner_email;
    @Column(unique = true)
    private String name;
    private String description;
    private Date date;
    private Time time;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Task(String owner_email, String name, String description, Date date, Time time){
        this.owner_email = owner_email;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
    }

}
