package com.example.task_manager.users.controllers;

import com.example.task_manager.users.entities.DeleteUserDTO;
import com.example.task_manager.users.entities.ProtectedUserDTO;
import com.example.task_manager.users.entities.RegisterUserDTO;
import com.example.task_manager.users.entities.UpdateUserDTO;
import com.example.task_manager.users.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find/")
    public ResponseEntity<ProtectedUserDTO> getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/create/")
    public ResponseEntity<String> createUser(@RequestBody RegisterUserDTO registerUserDTO, HttpServletRequest request){
        return userService.createUser(registerUserDTO,request);
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
