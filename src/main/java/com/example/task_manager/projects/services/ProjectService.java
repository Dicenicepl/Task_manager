package com.example.task_manager.projects.services;

import com.example.task_manager.projects.entities.DeleteProjectDTO;
import com.example.task_manager.projects.entities.Project;
import com.example.task_manager.projects.entities.ProtectedProjectDTO;
import com.example.task_manager.projects.entities.RegisterProjectDTO;
import com.example.task_manager.projects.repositories.ProjectRepository;
import com.example.task_manager.tokens.services.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TokenService tokenService;

    public ProjectService(ProjectRepository projectRepository, TokenService tokenService) {
        this.projectRepository = projectRepository;
        this.tokenService = tokenService;
    }
    private ProtectedProjectDTO converter(Project project){
        return new ProtectedProjectDTO(
                project.getOwner_email(),
                project.getName(),
                project.getDescription()
        );
    }

    public ResponseEntity<ProtectedProjectDTO> getProjectByName(String name){
        try {
            Project project = projectRepository.findProjectByNameStartingWith(name);
            if (project != null) {
                ProtectedProjectDTO data = converter(project);
                return ResponseEntity.ok(data);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NullPointerException e){
            return ResponseEntity.ok(null);
        }
    }

    public ResponseEntity<String> createProject(RegisterProjectDTO registerProjectDTO, String token) {
        try {
            if (registerProjectDTO.getName() == null || registerProjectDTO.getDescription() == null) {
                return ResponseEntity.badRequest().body("Invalid input. All fields must be provided.");
            }
            String email = tokenService.findAssignedEmailByToken(token);
            Project newProject = new Project(email, registerProjectDTO.getName(), registerProjectDTO.getDescription());
            projectRepository.save(newProject);

            return ResponseEntity.ok("Project created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("idk");
        }
    }


    public ResponseEntity<String> updateProject(RegisterProjectDTO registerProjectDTO){
        try {
            Project project = projectRepository.findProjectByNameStartingWith(registerProjectDTO.getName());
            Project newProject = new Project(
                    project.getProject_id(),
                    project.getOwner_email(),
                    registerProjectDTO.getName(),
                    registerProjectDTO.getDescription(),
                    project.getTasks()
            );
            projectRepository.save(newProject);
            return ResponseEntity.ok("Done");
        }catch (NullPointerException e){
            return ResponseEntity.badRequest().body("Body contain null");
        }
    }

    public ResponseEntity<String> deleteProject(DeleteProjectDTO deleteProjectDTO, String token){
        try {
            Project project = projectRepository.findProjectByNameStartingWith(deleteProjectDTO.getName());
            String email = tokenService.findAssignedEmailByToken(token);
            if (project.getOwner_email().equals(email)) {
                projectRepository.delete(project);
                return ResponseEntity.ok("Done");
            }
            return ResponseEntity.ok("Bad email");
        }catch (NullPointerException e){
            return ResponseEntity.badRequest().body("Body contain null");
        }
    }
    public Optional<Project> findProjectById(Long id){
        return projectRepository.findById(id);
    }
}
