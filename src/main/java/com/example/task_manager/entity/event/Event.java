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
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String email;
    String name;
    String description;
    public Event(String email, String name, String description){
        this.email = email;
        this.name = name;
        this.description = description;
    }

}
