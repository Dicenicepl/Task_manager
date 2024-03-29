package com.example.task_manager.users.controllers;

import com.example.task_manager.users.entities.DeleteUserDTO;
import com.example.task_manager.users.entities.ProtectedUserDTO;
import com.example.task_manager.users.entities.RegisterUserDTO;
import com.example.task_manager.users.entities.UpdateUserDTO;
import com.example.task_manager.users.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find/")
    public ResponseEntity<List<ProtectedUserDTO>> getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/create/")
    public ResponseEntity<String> createUser(@RequestBody RegisterUserDTO registerUserDTO){
        return userService.createUser(registerUserDTO);
    }
    @PutMapping("/update/")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDTO userData) {
        return userService.updateUser(userData);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<String> deleteUser(@RequestBody DeleteUserDTO deleteUserDTO) {
        return userService.deleteUser(deleteUserDTO);
    }
}
