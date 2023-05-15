package com.example.task_manager.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Calendar")
public class AppCalendary {

    @SequenceGenerator(
            name = "calendar_sequence",
            sequenceName = "calendar_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "calendar_sequence"
    )
    @Id
    private Long id;
    private Date date;
    private String eventName;
    private String eventDescription;
    private String eventOwnerEmail;
    private String eventGuestsEmails;
    private Boolean isSended = false;

    public AppCalendary(Date date, String eventName, String eventDescription, String eventOwner, String eventGuests) {
        this.date = date;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventOwnerEmail = eventOwner;
        this.eventGuestsEmails = eventGuests;
    }
}
