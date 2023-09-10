package com.example.task_manager.controller;

import com.example.task_manager.entity.event.Event;
import com.example.task_manager.entity.event.EventDTO;
import com.example.task_manager.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public void deleteUser(@PathVariable("token") String token, @PathVariable("id") Long idUserToDelete) {
        authService.deleteUser(idUserToDelete, token);
    }
    // Event controller


    @PostMapping("/event/create/")
    public ResponseEntity<String> saveEvent(@RequestBody Map<String, String> json) {
//        String token = json.get("token");
//        Event event = new Event(json.get("name"), json.get("description"));
//        if (!token.isBlank() && event.getName() != null) {
//            return authService.saveEvent(event, token);
//        }
//        return new ResponseEntity<>("Error", HttpStatus.OK);
        return authService.saveEvent(json);
    }

    @GetMapping("/event/get/")
    public ResponseEntity<EventDTO> getByIdEvent(String token, Long id) {
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
