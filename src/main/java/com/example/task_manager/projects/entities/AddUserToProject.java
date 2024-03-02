package com.example.task_manager.projects.entities;

import lombok.Data;

@Data
public class AddUserToProject {
    private String projectName;
    private String email;
}
