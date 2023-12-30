package com.example.task_manager.projects.controllers;

import com.example.task_manager.projects.entities.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProjectController {

    @GetMapping
    public ResponseEntity<String> getAllProjects(){
        return null;
    }

    @GetMapping
    public ResponseEntity<String> getProjectByName(String name){
        return null;
    }

    @PostMapping
    public ResponseEntity<String> createProject(Project project){
        return null;
    }

    @PutMapping
    public ResponseEntity<String> updateProject(Project project){
        return null;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProject(){
        return null;
    }
}
