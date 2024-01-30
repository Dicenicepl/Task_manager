package com.example.task_manager.projects.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterProjectDTO {
    private String owner_email;
    private String name;
    private String description;

    @Override
    public String toString() {
        return "RegisterProjectDTO{" +
                "owner_email='" + owner_email + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

