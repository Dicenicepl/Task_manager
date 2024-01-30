package com.example.task_manager.projects.services;

import com.example.task_manager.projects.entities.DeleteProjectDTO;
import com.example.task_manager.projects.entities.Project;
import com.example.task_manager.projects.entities.ProtectedProjectDTO;
import com.example.task_manager.projects.entities.RegisterProjectDTO;
import com.example.task_manager.projects.repositories.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    private ProtectedProjectDTO converter(Project project){
        return new ProtectedProjectDTO(
                project.getOwner_email(),
                project.getName(),
                project.getDescription(),
                project.getTasks()
        );
    }

    public ResponseEntity<ProtectedProjectDTO> getProjectByName(String name){
        try {
            ProtectedProjectDTO data = converter(projectRepository.findProjectByNameStartingWith(name));
            return ResponseEntity.ok(data);
        }catch (NullPointerException e){
            return ResponseEntity.ok(null);
        }
    }

    public ResponseEntity<String> createProject(RegisterProjectDTO registerProjectDTO) {
        try {
            if (registerProjectDTO.getOwner_email() == null || registerProjectDTO.getName() == null || registerProjectDTO.getDescription() == null) {
                return ResponseEntity.badRequest().body("Invalid input. All fields must be provided.");
            }

            Project newProject = new Project(registerProjectDTO.getOwner_email(), registerProjectDTO.getName(), registerProjectDTO.getDescription());
            projectRepository.save(newProject);

            return ResponseEntity.ok("Project created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("idk");
        }
    }


    public ResponseEntity<String> updateProject(RegisterProjectDTO registerProjectDTO){
        Project project = projectRepository.findProjectByNameStartingWith(registerProjectDTO.getName());
        Project newProject = new Project(
                project.getProject_id(),
                registerProjectDTO.getOwner_email(),
                registerProjectDTO.getName(),
                registerProjectDTO.getDescription(),
                project.getTasks()
        );
        projectRepository.save(newProject);
        return ResponseEntity.ok("Done");
    }

    public ResponseEntity<String> deleteProject(DeleteProjectDTO deleteProjectDTO){
        projectRepository.delete(projectRepository.findProjectByNameStartingWith(deleteProjectDTO.getName()));
        return ResponseEntity.ok("Done");
    }
}
