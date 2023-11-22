package com.example.task_manager.entity.event;

import java.sql.Date;
import java.sql.Time;

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
    private Date date;
    private Time time;

    public Event(String owner_email, String name, String description, Date date, Time time){
        this.owner_email = owner_email;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
    }

}
