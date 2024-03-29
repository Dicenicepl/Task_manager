package com.example.task_manager.projects.controllers;

import com.example.task_manager.projects.entities.AddUserToProject;
import com.example.task_manager.projects.entities.DeleteProjectDTO;
import com.example.task_manager.projects.entities.ProtectedProjectDTO;
import com.example.task_manager.projects.entities.RegisterProjectDTO;
import com.example.task_manager.projects.services.ProjectService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project/")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping("/find/")
    public ResponseEntity<ProtectedProjectDTO> getProjectByName(String name) {
        return projectService.getProjectByName(name);
    }

    @PostMapping("/create/")
    public ResponseEntity<String> createProject(@RequestBody RegisterProjectDTO registerProjectDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return projectService.createProject(registerProjectDTO, token);
    }

    @PutMapping("/update/")
    public ResponseEntity<String> updateProject(@RequestBody RegisterProjectDTO registerProjectDTO) {
        return projectService.updateProject(registerProjectDTO);
    }
    @PutMapping("/add/user/")
    public ResponseEntity<String> addUserToProject(@RequestBody AddUserToProject addUserToProject){
        return projectService.addUserToProject(addUserToProject);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<String> deleteProject(@RequestBody DeleteProjectDTO deleteProjectDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return projectService.deleteProject(deleteProjectDTO, token);
    }
}
