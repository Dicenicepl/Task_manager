package com.example.task_manager.entity.event;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "events")
public class Event {
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
    private String project_users;

    public Event(String owner_email, String name, String description){
        this.owner_email = owner_email;
        this.name = name;
        this.description = description;
    }

    public Event(String owner_email, String name, String description, String project_users) {
        this.owner_email = owner_email;
        this.name = name;
        this.description = description;
        this.project_users = project_users;
    }
}
