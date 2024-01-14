package com.example.task_manager.projects.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteProjectData {

    private String owner_email;
    private String name;

}
