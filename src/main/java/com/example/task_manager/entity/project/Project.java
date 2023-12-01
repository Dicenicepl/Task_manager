package com.example.task_manager.entity.project;


import com.example.task_manager.entity.event.Event;
import jakarta.persistence.*;
import lombok.*;

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
    private Long project_id;
    private String project_owner_email; // TODO: 01.12.2023 when getting data, display with User.username
    private String project_name;
    private String project_description;
    private int number_of_events = 0;
    private int number_of_users = 0;
    private int[] events_id;
    private int[] users_id;

    public Project(String project_owner_email,String project_name, String description) {
        this.project_owner_email = project_owner_email;
        this.project_name = project_name;
        this.project_description = description;
    }
}
