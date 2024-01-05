package com.example.task_manager.tasks.controllers;

import com.example.task_manager.tasks.entities.ProtectedTaskData;
import com.example.task_manager.tasks.entities.Task;
import com.example.task_manager.tasks.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/t/")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/list/tasks/")
    public ResponseEntity<List<ProtectedTaskData>> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/find/task/")
    public ResponseEntity<ProtectedTaskData> getTaskByName(String name) {
        return taskService.getTaskByName(name);
    }

    @PostMapping("/create/task/")
    public ResponseEntity<String> createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/update/task/")
    public ResponseEntity<String> updateTask(@RequestBody Task task) {
        return taskService.updateTask(task);
    }

    @DeleteMapping("/delete/task/")
    public ResponseEntity<String> deleteTask(@RequestBody Task task) {
        return taskService.deleteTask(task);
    }
}
