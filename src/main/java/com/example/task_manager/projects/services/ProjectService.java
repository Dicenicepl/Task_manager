package com.example.task_manager.projects.services;

import com.example.task_manager.projects.entities.DeleteProjectData;
import com.example.task_manager.projects.entities.Project;
import com.example.task_manager.projects.entities.ProtectedProjectData;
import com.example.task_manager.projects.entities.RegisterProjectData;
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
    private ProtectedProjectData converter(Project project){
        return new ProtectedProjectData(
                project.getOwner_email(),
                project.getName(),
                project.getDescription(),
                project.getTasks()
        );
    }
    public ResponseEntity<List<ProtectedProjectData>> getAllProjects(){
        List<ProtectedProjectData> list = new ArrayList<>();
        for (Project project:projectRepository.findAll()){
            list.add(converter(project));
        }
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<ProtectedProjectData> getProjectByName(String name){
        ProtectedProjectData data = converter(projectRepository.findByName(name));
        return ResponseEntity.ok(data);
    }

    public ResponseEntity<String> createProject(RegisterProjectData registerProjectData){
        projectRepository.save(new Project(registerProjectData.getOwner_email(), registerProjectData.getName(), registerProjectData.getDescription()));
        return ResponseEntity.ok("Done");

    }

    public ResponseEntity<String> updateProject(RegisterProjectData registerProjectData){
        Project project = projectRepository.findByName(registerProjectData.getName());
        Project newProject = new Project(
                project.getProject_id(),
                registerProjectData.getOwner_email(),
                registerProjectData.getName(),
                registerProjectData.getDescription(),
                project.getTasks()
        );
        projectRepository.save(newProject);
        return ResponseEntity.ok("Done");
    }

    public ResponseEntity<String> deleteProject(DeleteProjectData deleteProjectData){
        projectRepository.delete(projectRepository.findByName(deleteProjectData.getName()));
        return ResponseEntity.ok("Done");
    }
}
