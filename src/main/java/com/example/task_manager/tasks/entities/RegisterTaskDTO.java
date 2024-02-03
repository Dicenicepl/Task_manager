package com.example.task_manager.tasks.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterTaskDTO {
    String owner_email;
    String name;
    String assignedTo;
    String description;
    Long endingTimeInMinis;

    public RegisterTaskDTO(String owner_email, String name, String assignedTo, String description, Long endingTimeInMinis) {
        this.owner_email = owner_email;
        this.name = name;
        this.assignedTo = assignedTo;
        this.description = description;
        this.endingTimeInMinis = endingTimeInMinis;
    }

    @Override
    public String toString() {
        return "RegisterTaskDTO{" +
                "owner_email='" + owner_email + '\'' +
                ", name='" + name + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", description='" + description + '\'' +
                ", endingTimeInMinis=" + endingTimeInMinis +
                '}';
    }
}

