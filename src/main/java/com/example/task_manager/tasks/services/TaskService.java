package com.example.task_manager.tasks.services;

import com.example.task_manager.projects.entities.Project;
import com.example.task_manager.projects.services.ProjectService;
import com.example.task_manager.tasks.entities.ProtectedTaskDTO;
import com.example.task_manager.tasks.entities.Task;
import com.example.task_manager.tasks.entities.TaskStatus;
import com.example.task_manager.tasks.repositories.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectService projectService;

    public TaskService(TaskRepository taskRepository, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
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

    public ResponseEntity<String> createTask(@RequestBody Task task, Long project) {
        if (isProjectExists(project)) {
            task.setProject(projectService.findProjectById(project).get());
            //todo change
            task.setStatus(TaskStatus.IN_PROGRESS);
            taskRepository.save(task);
            return ResponseEntity.ok("Created");
        }
        return ResponseEntity.ok("Project not found");
    }

    public ResponseEntity<String> updateTask(Task task, Long project) {
        if (isProjectExists(project)) {
        }
        return null;
    }

    public ResponseEntity<String> deleteTask(Task task, Long project) {
        if (isProjectExists(project)) {
            taskRepository.delete(task);
            return ResponseEntity.ok("Done");
        }
        return ResponseEntity.ok("Project not found");
    }
}
