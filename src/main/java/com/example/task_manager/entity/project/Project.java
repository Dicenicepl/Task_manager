package com.example.task_manager.entity.project;


import com.example.task_manager.entity.task.Task;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @SequenceGenerator(
            name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long id;
    private String owner_email;
    private String name;
    private String description;
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;
    private int number_of_users = 0;

    public Project(String owner_email, String name, String description) {
        this.owner_email = owner_email;
        this.name = name;
        this.description = description;
    }
}
