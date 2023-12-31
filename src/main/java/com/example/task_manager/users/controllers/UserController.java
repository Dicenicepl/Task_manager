package com.example.task_manager.users.controllers;

import com.example.task_manager.users.entities.ProtectedUser;
import com.example.task_manager.users.entities.User;
import com.example.task_manager.users.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list/users/")
    public ResponseEntity<List<ProtectedUser>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/find/user/")
    public ResponseEntity<ProtectedUser> getUserByEmail(String email){
        return userService.getUserByEmail(email);
    }

    @PostMapping("/create/user/")
    public ResponseEntity<String> createUser(User user){
        return null;
    }

    @PutMapping("/update/user/")
    public ResponseEntity<String> updateUser(User user){
        return null;
    }

    @DeleteMapping("/delete/user/")
    public ResponseEntity<String> deleteUser(){
        return null;
    }
}
