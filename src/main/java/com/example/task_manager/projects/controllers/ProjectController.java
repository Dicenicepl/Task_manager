package com.example.task_manager.projects.controllers;

import com.example.task_manager.projects.entities.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProjectController {

    @GetMapping("/list/projects/")
    public ResponseEntity<String> getAllProjects(){
        return null;
    }

    @GetMapping("/find/project")
    public ResponseEntity<String> getProjectByName(String name){
        return null;
    }

    @PostMapping("/create/project/")
    public ResponseEntity<String> createProject(Project project){
        return null;
    }

    @PutMapping("/update/project/")
    public ResponseEntity<String> updateProject(Project project){
        return null;
    }

    @DeleteMapping("/delete/project/")
    public ResponseEntity<String> deleteProject(){
        return null;
    }
}
