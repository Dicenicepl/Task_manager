package com.example.task_manager.projects.entities;

import com.example.task_manager.tasks.entities.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProtectedProjectData {
    private String owner_email;
    private String name;
    private String description;
    private List<Task> tasks;
}
