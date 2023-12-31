package com.example.task_manager.users.controllers;

import com.example.task_manager.users.entities.ProtectedUserData;
import com.example.task_manager.users.entities.RegisterData;
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
    public ResponseEntity<List<ProtectedUserData>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/find/user/")
    public ResponseEntity<ProtectedUserData> getUserByEmail(String email){
        return userService.getUserByEmail(email);
    }

    @PostMapping("/create/user/")
    public ResponseEntity<String> createUser(RegisterData registerData) {
        return userService.createUser(registerData);
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
