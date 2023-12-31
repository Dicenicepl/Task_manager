package com.example.task_manager.projects.entities;

import com.example.task_manager.tasks.entities.Task;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Projects")
public class Project {
    @Id
    @SequenceGenerator(
            name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long project_id;
    private String owner_email;
    @Column(unique = true)
    private String name;
    private String description;
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    public Project(String owner_email, String name, String description) {
        this.owner_email = owner_email;
        this.name = name;
        this.description = description;
    }
}
