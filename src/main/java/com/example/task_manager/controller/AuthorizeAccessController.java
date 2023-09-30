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

    @DeleteMapping("/user/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable("email") String email,
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
    public List<EventDTO> getAllEventsWhereUserIsPinged(String token) {
        return authService.getAllEvents(token);
    }

    @DeleteMapping("/event/delete/{name}")
    public ResponseEntity<String> deleteEvent(@PathVariable("name") String name,
                                              @RequestBody String token) {
        return authService.deleteEvent(name, token);
    }
    @PostMapping("/event/update/")
    public ResponseEntity<String> updateEvent(){
        return null;
    }
    @PutMapping("/event/add/user/{email}")
    public ResponseEntity<String> addUserToProject(@PathVariable("email") String email,
                                                   @RequestBody String token){
        return null;
    }
    @DeleteMapping("/event/delete/user/{email}")
    public ResponseEntity<String> deleteUserFromProject(@PathVariable("email") String email,
                                                        @RequestBody String token){
        return null;
    }
    @GetMapping("/event/get/user/")
    public ResponseEntity<String> getAllUsersFromProject(String name,
                                                         @RequestBody String token){
        return null;
    }
    @PutMapping("/test")
    public String test(String name){
        return name;
    }


}
