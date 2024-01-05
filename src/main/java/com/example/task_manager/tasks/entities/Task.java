package com.example.task_manager.tasks.entities;

import com.example.task_manager.projects.entities.Project;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Tasks")
public class Task {
    @Id
    @SequenceGenerator(
            name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long task_id;
    private String owner_email;
    @Column(unique = true)
    private String name;
    private String description;
    private Long creatingTimeInMinis = System.currentTimeMillis();
    private Long endingTimeInMinis;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Task(String owner_email, String name, String description, Long endingTimeInMinis, Project project) {
        this.owner_email = owner_email;
        this.name = name;
        this.description = description;
        this.endingTimeInMinis = endingTimeInMinis;
        this.project = project;
    }
}
