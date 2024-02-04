package com.example.task_manager.tasks.controllers;

import com.example.task_manager.tasks.entities.DeleteTaskDTO;
import com.example.task_manager.tasks.entities.ProtectedTaskDTO;
import com.example.task_manager.tasks.entities.RegisterTaskDTO;
import com.example.task_manager.tasks.entities.UpdateTaskDTO;
import com.example.task_manager.tasks.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/task/")
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
    public ResponseEntity<String> createTask(@RequestBody RegisterTaskDTO registerTaskDTO, Long project) {
        return taskService.createTask(registerTaskDTO, project);
    }

    @PutMapping("/update/task/")
    public ResponseEntity<String> updateTask(@RequestBody UpdateTaskDTO updateTaskDTO, Long project) {
        return taskService.updateTask(updateTaskDTO, project);
    }

    @DeleteMapping("/delete/task/")
    public ResponseEntity<String> deleteTask(@RequestBody DeleteTaskDTO deleteTaskDTO,@RequestHeader(name = "Authorization") String token, Long project) {
        return taskService.deleteTask(deleteTaskDTO,token, project);
    }
}
