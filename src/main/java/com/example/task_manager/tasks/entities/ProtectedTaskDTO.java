package com.example.task_manager.tasks.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
public class ProtectedTaskDTO {
    private String owner_email;
    private String name;
    private String description;
    private Time timeOfCreating;
    private Date dateOfCreating;
    private Time timeOfEnding;
    private Date dateOfEnding;
    @Override
    public String toString() {
        return "ProtectedTaskDTO{" +
                "owner_email='" + owner_email + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", timeOfCreating=" + timeOfCreating +
                ", dateOfCreating=" + dateOfCreating +
                ", timeOfEnding=" + timeOfEnding +
                ", dateOfEnding=" + dateOfEnding +
                '}';
    }
}
