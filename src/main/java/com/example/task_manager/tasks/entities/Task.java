package com.example.task_manager.tasks.entities;

import com.example.task_manager.projects.entities.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tasks")
public class Task {
    //todo assignedTo create a userFinder for assigning
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
    private String assignedTo;
    private String description;
    private TaskStatus status;
    private Long creatingTimeInMinis = System.currentTimeMillis();
    private Long endingTimeInMinis;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project = new Project();


    public Task(String owner_email, String name, String assignedTo, String description, TaskStatus status) {
        this.owner_email = owner_email;
        this.name = name;
        this.assignedTo = assignedTo;
        this.description = description;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task_id=" + task_id +
                ", owner_email='" + owner_email + '\'' +
                ", name='" + name + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", creatingTimeInMinis=" + creatingTimeInMinis +
                ", endingTimeInMinis=" + endingTimeInMinis +
                ", project=" + project +
                '}';
    }
}
