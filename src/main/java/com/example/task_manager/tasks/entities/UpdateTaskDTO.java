package com.example.task_manager.tasks.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskDTO {
    String name;
    String assignedTo;
    String description;
    TaskStatus status;
    Long endingTimeInMinis;

    public UpdateTaskDTO(String name, String assignedTo, String description, TaskStatus status, Long endingTimeInMinis) {
        this.name = name;
        this.assignedTo = assignedTo;
        this.description = description;
        this.status = status;
        this.endingTimeInMinis = endingTimeInMinis;
    }

    @Override
    public String toString() {
        return "UpdateTaskDTO{" +
                "name='" + name + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", endingTimeInMinis=" + endingTimeInMinis +
                '}';
    }
}
