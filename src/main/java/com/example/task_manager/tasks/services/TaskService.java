package com.example.task_manager.tasks.services;

import com.example.task_manager.projects.services.ProjectService;
import com.example.task_manager.tasks.entities.ProtectedTaskDTO;
import com.example.task_manager.tasks.entities.Task;
import com.example.task_manager.tasks.repositories.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectService projectService;

    public TaskService(TaskRepository taskRepository, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
    }

    private ProtectedTaskDTO convertTaskToProtectedTaskData(Task task){
        return new ProtectedTaskDTO(
                task.getOwner_email(),
                task.getName(),
                task.getDescription(),
                new Time(task.getCreatingTimeInMinis()),
                new Date(task.getCreatingTimeInMinis()),
                new Time(task.getEndingTimeInMinis()),
                new Date(task.getEndingTimeInMinis()),
                task.getProject());
    }

    public ResponseEntity<Set<ProtectedTaskDTO>> getAllTasks(int project){

        Set<Task> tasks = taskRepository.findTaskByProject(project);
        Set<ProtectedTaskDTO> protectedTaskData = new HashSet<>();
        if (tasks != null) {
            for (Task task : tasks) {
                protectedTaskData.add(convertTaskToProtectedTaskData(task));
            }
            return new ResponseEntity<>(protectedTaskData, HttpStatus.OK);
        }
        return ResponseEntity.ok(null);
    }

    public ResponseEntity<ProtectedTaskDTO> getTaskByName(String taskName) {
        Task task = taskRepository.findTaskByName(taskName);
        return new ResponseEntity<>(convertTaskToProtectedTaskData(task), HttpStatus.OK);
    }

    public ResponseEntity<String> createTask(Task task) {
        return null;
    }

    public ResponseEntity<String> updateTask(Task task) {
        return null;
    }

    public ResponseEntity<String> deleteTask(Task task) {
        return null;
    }
}
