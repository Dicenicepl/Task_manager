package com.example.task_manager.tasks.services;

import com.example.task_manager.projects.entities.Project;
import com.example.task_manager.projects.services.ProjectService;
import com.example.task_manager.tasks.entities.*;
import com.example.task_manager.tasks.repositories.TaskRepository;
import com.example.task_manager.tokens.services.TokenService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskService {
    //todo try catch
    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final TokenService tokenService;


    public TaskService(TaskRepository taskRepository, ProjectService projectService, TokenService tokenService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
        this.tokenService = tokenService;
    }

    private ProtectedTaskDTO convertTaskToProtectedTaskData(Task task) {
        return new ProtectedTaskDTO(
                task.getOwner_email(),
                task.getName(),
                task.getDescription(),
                new Time(task.getCreatingTimeInMinis()),
                new Date(task.getCreatingTimeInMinis()),
                new Time(task.getEndingTimeInMinis()),
                new Date(task.getEndingTimeInMinis())
        );
    }

    //todo create checker that will check if user can modifying data
    private boolean isProjectExists(Long id) {
        if (id != null) {
            Optional<Project> project = projectService.findProjectById(id);
            return project.isPresent();
        }
        return false;
    }

    public ResponseEntity<Set<ProtectedTaskDTO>> getAllTasks(Long project) {
        if (isProjectExists(project)) {
            Set<Task> tasks = taskRepository.findTaskByProject(project);
            Set<ProtectedTaskDTO> protectedTaskData = new HashSet<>();
            if (tasks != null) {
                for (Task task : tasks) {
                    protectedTaskData.add(convertTaskToProtectedTaskData(task));
                }
                return new ResponseEntity<>(protectedTaskData, HttpStatus.OK);
            }
        }
        return ResponseEntity.ok(null);
    }

    public ResponseEntity<ProtectedTaskDTO> getTaskByName(String taskName, Long project) {
        if (isProjectExists(project)) {
            Task task = taskRepository.findTaskByName(taskName);
            if (task != null) {
                return new ResponseEntity<>(convertTaskToProtectedTaskData(task), HttpStatus.OK);
            }
        }
        return ResponseEntity.ok(null);
    }

    public ResponseEntity<String> createTask(RegisterTaskDTO register, Long project) {
        if (isProjectExists(project)) {
            try{
                if (register.getName() != null || register.getEndingTimeInMinis() != null) {
                    Task task = new Task(
                            register.getOwner_email(),
                            register.getName(),
                            register.getAssignedTo(),
                            register.getDescription(),
                            register.getEndingTimeInMinis()
                    );
                    task.setProject(projectService.findProjectById(project).get());
                    taskRepository.save(task);
                    return ResponseEntity.ok("Created");
                }
                return ResponseEntity.badRequest().body(null);
            }catch (NullPointerException | DataIntegrityViolationException e){
                return ResponseEntity.badRequest().body(null);
            }
        }
        return ResponseEntity.ok("Project not found");
    }

    public ResponseEntity<String> updateTask(UpdateTaskDTO updateTaskDTO, Long project) {
        if (isProjectExists(project)) {
            Task oldTask = taskRepository.findTaskByName(updateTaskDTO.getName());
            Task newTask = new Task(
                    oldTask.getTask_id(),
                    oldTask.getOwner_email(),
                    oldTask.getName(),
                    updateTaskDTO.getAssignedTo(),
                    updateTaskDTO.getDescription(),
                    updateTaskDTO.getStatus(),
                    oldTask.getCreatingTimeInMinis(),
                    updateTaskDTO.getEndingTimeInMinis()
            );
            taskRepository.save(newTask);
            return ResponseEntity.ok("Saved");
        }
        return ResponseEntity.ok("Nuh uh");
    }

    //todo copy permission checker
    public ResponseEntity<String> deleteTask(DeleteTaskDTO task, String token, Long project) {
        if (isProjectExists(project)) {
            String emailFromToken = tokenService.findAssignedEmailByToken(token);
            Task task1 = taskRepository.findTaskByName(task.getName());
            if (task1.getOwner_email().equals(emailFromToken)) {
                taskRepository.delete(task1);
                return ResponseEntity.ok("Done");
            }
            return ResponseEntity.ok("No permission");
        }
        return ResponseEntity.ok("Project not found");
    }
}
