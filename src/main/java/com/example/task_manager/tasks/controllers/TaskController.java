package com.example.task_manager.tasks.controllers;

import com.example.task_manager.tasks.entities.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    @GetMapping
    public ResponseEntity<String> getAllTasks(){
        return null;
    }

    @GetMapping
    public ResponseEntity<String> getTaskByName(String name){
        return null;
    }

    @PostMapping
    public ResponseEntity<String> createTask(Task task){
        return null;
    }

    @PutMapping
    public ResponseEntity<String> updateTask(Task task){
        return null;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTask(){
        return null;
    }
}
