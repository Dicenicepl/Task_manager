package com.example.task_manager.entity.event;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "events")
// TODO: 08.11.2023 add sql.Date as a variable
public class Event {
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

    public Event(String owner_email, String name, String description){
        this.owner_email = owner_email;
        this.name = name;
        this.description = description;
    }

}
