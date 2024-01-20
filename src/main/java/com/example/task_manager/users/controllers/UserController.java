package com.example.task_manager.users.controllers;

import com.example.task_manager.users.entities.DeleteUserDTO;
import com.example.task_manager.users.entities.ProtectedUserDTO;
import com.example.task_manager.users.entities.UpdateUserDTO;
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
    public ResponseEntity<List<ProtectedUserDTO>> getAllUsers() {
        return userService.getAllUsers();

    }


    @GetMapping("/find/user/")
    public ResponseEntity<ProtectedUserDTO> getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }


    @PutMapping("/update/user/")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDTO userData) {
        return userService.updateUser(userData);
    }

    @DeleteMapping("/delete/user/")
    public ResponseEntity<String> deleteUser(@RequestBody DeleteUserDTO deleteUserDTO) {
        return userService.deleteUser(deleteUserDTO);
    }
}
