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

    @GetMapping("/user/login/")
    public String login(String email, String password) {
        return authService.login(email, password);
    }
    @GetMapping("/user/logout/")
    public void logout(String token){
        authService.logout(token);
    }

    @DeleteMapping("/user/delete/")
    public ResponseEntity<String> deleteUser(String email,
                                             @RequestBody String token) {
        return authService.deleteUser(email, token);
    }
    @PutMapping("/user/update/")
    public ResponseEntity<String> updateUser(@RequestBody Map<String, String> json){
        return authService.updateUser(json);
    }

    // Event controller
    @PostMapping("/event/create/")
    public ResponseEntity<String> saveEvent(@RequestBody Map<String, String> json) {
        return authService.saveEvent(json);
    }

    // TODO: 29.09.2023 change method for sending events where current user is pinged
    @GetMapping("/event/get/all/")
    public List<EventDTO> getAllEventsWhereUserIsPinged(@RequestBody String token) {
        return authService.getAllEvents(token);
    }

    @DeleteMapping("/event/delete/")
    public ResponseEntity<String> deleteEvent(String name,
                                              @RequestBody String token) {
        return authService.deleteEvent(name, token);
    }
    @PostMapping("/event/update/")
    public ResponseEntity<String> updateEvent(){
        return null;
    }
    @PutMapping("/event/add/user/")
    public ResponseEntity<String> addUserToProject(String email,
                                                   @RequestBody String token){
        return null;
    }
    @DeleteMapping("/event/delete/user/")
    public ResponseEntity<String> deleteUserFromProject(String email,
                                                        @RequestBody String token){
        return null;
    }
    @GetMapping("/event/get/user/")
    public ResponseEntity<String> getAllUsersFromProject(String name,
                                                         @RequestBody String token){
        return null;
    }

}
