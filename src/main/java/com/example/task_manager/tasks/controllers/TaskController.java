package com.example.task_manager.tasks.controllers;

import com.example.task_manager.tasks.entities.ProtectedTaskDTO;
import com.example.task_manager.tasks.entities.Task;
import com.example.task_manager.tasks.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/t/")
//todo check where is saved task (project). If is out of range then return bad request,else let continue operations
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/list/tasks/")
    public ResponseEntity<List<ProtectedTaskDTO>> getAllTasks() {
        return taskService.getAllTasks();
    }
    @GetMapping("/find/task/")
    public ResponseEntity<ProtectedTaskDTO> getTaskByName(String name) {
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
