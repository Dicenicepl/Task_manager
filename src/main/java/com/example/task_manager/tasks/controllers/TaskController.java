package com.example.task_manager.tasks.controllers;

import com.example.task_manager.tasks.entities.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    @GetMapping("/list/tasks/")
    public ResponseEntity<String> getAllTasks(){
        return null;
    }

    @GetMapping("/find/task/")
    public ResponseEntity<String> getTaskByName(String name){
        return null;
    }

    @PostMapping("/create/task/")
    public ResponseEntity<String> createTask(Task task){
        return null;
    }

    @PutMapping("/update/task/")
    public ResponseEntity<String> updateTask(Task task){
        return null;
    }

    @DeleteMapping("/delete/task/")
    public ResponseEntity<String> deleteTask(){
        return null;
    }
}
