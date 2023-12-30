package com.example.task_manager.users.controllers;

import com.example.task_manager.users.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping
    public ResponseEntity<String> getAllUsers(){
        return null;
    }

    @GetMapping
    public ResponseEntity<String> getUserByName(String name){
        return null;
    }

    @PostMapping
    public ResponseEntity<String> createUser(User user){
        return null;
    }

    @PutMapping
    public ResponseEntity<String> updateUser(User user){
        return null;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(){
        return null;
    }
}
