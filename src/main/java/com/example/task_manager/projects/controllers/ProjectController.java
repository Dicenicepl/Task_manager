package com.example.task_manager.projects.controllers;

import com.example.task_manager.projects.entities.DeleteProjectDTO;
import com.example.task_manager.projects.entities.ProtectedProjectDTO;
import com.example.task_manager.projects.entities.RegisterProjectDTO;
import com.example.task_manager.projects.repositories.ProjectRepository;
import com.example.task_manager.projects.services.ProjectService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/p/")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectRepository projectRepository;

    public ProjectController(ProjectService projectService,
                             ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }


    @GetMapping("/find/project")
    public ResponseEntity<ProtectedProjectDTO> getProjectByName(String name){
        return projectService.getProjectByName(name);
    }

    //todo create a getter email from token_table and save as owner_email
    @PostMapping("/create/project/")
    public ResponseEntity<String> createProject(@RequestBody RegisterProjectDTO registerProjectDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        System.out.println(authorization);
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
