package com.example.task_manager.projects.entities;

import com.example.task_manager.tasks.entities.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public String toString() {
        return "Project{" +
                "project_id=" + project_id +
                ", owner_email='" + owner_email + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
