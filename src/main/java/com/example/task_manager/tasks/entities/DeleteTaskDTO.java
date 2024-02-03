package com.example.task_manager.tasks.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteTaskDTO {
    String name;
    String owner_email;

    public DeleteTaskDTO(String name, String owner_email) {
        this.name = name;
        this.owner_email = owner_email;
    }

    @Override
    public String toString() {
        return "DeleteTaskDTO{" +
                "name='" + name + '\'' +
                ", owner_email='" + owner_email + '\'' +
                '}';
    }
}
