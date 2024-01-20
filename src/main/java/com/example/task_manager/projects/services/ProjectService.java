package com.example.task_manager.projects.services;

import com.example.task_manager.projects.entities.DeleteProjectDTO;
import com.example.task_manager.projects.entities.Project;
import com.example.task_manager.projects.entities.ProtectedProjectDTO;
import com.example.task_manager.projects.entities.RegisterProjectDTO;
import com.example.task_manager.projects.repositories.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<List<ProtectedProjectDTO>> getAllProjects(){
        List<ProtectedProjectDTO> list = new ArrayList<>();
        for (Project project:projectRepository.findAll()){
            list.add(converter(project));
        }
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<ProtectedProjectDTO> getProjectByName(String name){
        ProtectedProjectDTO data = converter(projectRepository.findByName(name));
        return ResponseEntity.ok(data);
    }

    public ResponseEntity<String> createProject(RegisterProjectDTO registerProjectDTO){
        projectRepository.save(new Project(registerProjectDTO.getOwner_email(), registerProjectDTO.getName(), registerProjectDTO.getDescription()));
        return ResponseEntity.ok("Done");

    }

    public ResponseEntity<String> updateProject(RegisterProjectDTO registerProjectDTO){
        Project project = projectRepository.findByName(registerProjectDTO.getName());
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
        projectRepository.delete(projectRepository.findByName(deleteProjectDTO.getName()));
        return ResponseEntity.ok("Done");
    }
}
