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
    
    /*
    json{
        email:
        password:
    }
     */
    @PostMapping("/user/login/")
    public ResponseEntity<String> login(@RequestBody Map<String, String> json) {
        return authService.login(json);
    }
    /*
    localhost:PORT/auth/user/logout/?email= &token= 
     */
    @GetMapping("/user/logout/")
    public void logout(String email, String token){
        authService.logout(email, token);
    }
    /*
    json{
        email:
        token:
    }
     */
    @DeleteMapping("/user/delete/")
    public ResponseEntity<String> deleteUser(@RequestBody Map<String, String> json) {
        return authService.deleteUser(json);
    }
    /*
    json{
        username:
        email:
        password:
        token:
    }
     */
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
    @PutMapping("/event/update/")
    public ResponseEntity<String> updateEvent(@RequestBody Map<String, String> json){
        return authService.updateEvent(json);
    }
    /*
    json{
    token:
    name:
    description:
    }
     */
    @PostMapping("/project/create/")
    public ResponseEntity<String> createProject(@RequestBody Map<String, String> json){
        return authService.createProject(json);
    }
    /*
    json{
    token:
    name:
    description:
    }
   */
    @PutMapping("/project/update/")
    public ResponseEntity<String> updateProject(@RequestBody Map<String, String> json){
        return authService.updateProject(json);
    }

}
