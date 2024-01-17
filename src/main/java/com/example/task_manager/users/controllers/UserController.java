package com.example.task_manager.users.controllers;

import com.example.task_manager.users.entities.*;
import com.example.task_manager.users.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/u/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/list/users/")
    public ResponseEntity<List<ProtectedUserData>> getAllUsers() {
        return userService.getAllUsers();

    }


    @GetMapping("/find/user/")
    public ResponseEntity<ProtectedUserData> getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/create/user/")
    public ResponseEntity<String> createUser(@RequestBody RegisterUserData registerData) {
        return userService.createUser(registerData);
    }

    @PutMapping("/update/user/")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserData userData) {
        return userService.updateUser(userData);
    }

    @DeleteMapping("/delete/user/")
    public ResponseEntity<String> deleteUser(@RequestBody DeleteUserData deleteUserData) {
        return userService.deleteUser(deleteUserData);
    }
}
