package com.example.task_manager.projects.controllers;

import com.example.task_manager.projects.entities.DeleteProjectDTO;
import com.example.task_manager.projects.entities.ProtectedProjectDTO;
import com.example.task_manager.projects.entities.RegisterProjectDTO;
import com.example.task_manager.projects.services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/p/")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/list/projects/")
    public ResponseEntity<List<ProtectedProjectDTO>> getAllProjects(){
        return projectService.getAllProjects();
    }

    @GetMapping("/find/project")
    public ResponseEntity<ProtectedProjectDTO> getProjectByName(String name){
        return projectService.getProjectByName(name);
    }

    @PostMapping("/create/project/")
    public ResponseEntity<String> createProject(RegisterProjectDTO registerProjectDTO){
        return projectService.createProject(registerProjectDTO);
    }

    @PutMapping("/update/project/")
    public ResponseEntity<String> updateProject(RegisterProjectDTO registerProjectDTO){
        return projectService.updateProject(registerProjectDTO);
    }

    @DeleteMapping("/delete/project/")
    public ResponseEntity<String> deleteProject(DeleteProjectDTO deleteProjectDTO){
        return projectService.deleteProject(deleteProjectDTO);
    }
}
