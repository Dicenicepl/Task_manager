package com.example.task_manager.tasks.controllers;

import com.example.task_manager.tasks.entities.ProtectedTaskDTO;
import com.example.task_manager.tasks.entities.Task;
import com.example.task_manager.tasks.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/t/")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/list/tasks/")
    public ResponseEntity<Set<ProtectedTaskDTO>> getAllTasks(Long project) {
        return taskService.getAllTasks(project);
    }
    @GetMapping("/find/task/")
    public ResponseEntity<ProtectedTaskDTO> getTaskByName(String taskName, Long project) {
        return taskService.getTaskByName(taskName, project);
    }

    @PostMapping("/create/task/")
    public ResponseEntity<String> createTask(@RequestBody Task task, Long project) {
        return taskService.createTask(task, project);
    }

    @PutMapping("/update/task/")
    public ResponseEntity<String> updateTask(@RequestBody Task task, Long project) {
        return taskService.updateTask(task, project);
    }

    @DeleteMapping("/delete/task/")
    public ResponseEntity<String> deleteTask(@RequestBody Task task, Long project) {
        return taskService.deleteTask(task, project);
    }
}
