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
    private String owner_email; // TODO: 01.12.2023 when getting data, display with User.username
    private String name;
    private String description;
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;
    private int number_of_users = 0;
//    private int[] events_id;
//    private int[] users_id;

    public Project(String owner_email,String name, String description) {
        this.owner_email = owner_email;
        this.name = name;
        this.description = description;
    }

    public Project(Long id, String owner_email, String name, String description) {
        this.id = id;
        this.owner_email = owner_email;
        this.name = name;
        this.description = description;
    }
}
