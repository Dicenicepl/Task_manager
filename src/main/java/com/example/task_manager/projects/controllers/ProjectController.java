package com.example.task_manager.projects.controllers;

import com.example.task_manager.projects.entities.DeleteProjectData;
import com.example.task_manager.projects.entities.Project;
import com.example.task_manager.projects.entities.ProtectedProjectData;
import com.example.task_manager.projects.entities.RegisterProjectData;
import com.example.task_manager.projects.services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/list/projects/")
    public ResponseEntity<List<ProtectedProjectData>> getAllProjects(){
        return projectService.getAllProjects();
    }

    @GetMapping("/find/project")
    public ResponseEntity<ProtectedProjectData> getProjectByName(String name){
        return projectService.getProjectByName(name);
    }

    @PostMapping("/create/project/")
    public ResponseEntity<String> createProject(RegisterProjectData registerProjectData){
        return projectService.createProject(registerProjectData);
    }

    @PutMapping("/update/project/")
    public ResponseEntity<String> updateProject(RegisterProjectData registerProjectData){
        return projectService.updateProject(registerProjectData);
    }

    @DeleteMapping("/delete/project/")
    public ResponseEntity<String> deleteProject(DeleteProjectData deleteProjectData){
        return projectService.deleteProject(deleteProjectData);
    }
}
