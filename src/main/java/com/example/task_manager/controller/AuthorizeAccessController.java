package com.example.task_manager.controller;

import com.example.task_manager.entity.event.EventDTO;
import com.example.task_manager.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthorizeAccessController {

    private final AuthService authService;

    public AuthorizeAccessController(AuthService authService) {
        this.authService = authService;
    }

    // User controller

    @GetMapping("/user/login/")
    public String login(String email, String password) {
        return authService.login(email, password);
    }
    @GetMapping("/user/logout/")
    public void logout(String token){
        authService.logout(token);
    }

    @DeleteMapping("/user/delete/{token}/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("token") String token, @PathVariable("id") Long idUserToDelete) {
        return authService.deleteUser(idUserToDelete, token);
    }
    // Event controller


    @PostMapping("/event/create/")
    public ResponseEntity<String> saveEvent(@RequestBody Map<String, String> json) {
        return authService.saveEvent(json);
    }

    @GetMapping("/event/get/")
    public ResponseEntity<String> getByIdEvent(String token, Long id) {
        return authService.getByIdEvent(token, id);
    }

    @GetMapping("/event/get/all/")
    public List<EventDTO> getAllEvents(String token) {
        return authService.getAllEvents(token);
    }

    @DeleteMapping("/event/delete/{token}/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("token") String token, @PathVariable("id") Long idUserToDelete) {
        return authService.deleteEvent(idUserToDelete, token);
    }

}
