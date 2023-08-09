package com.example.task_manager.controller;

import com.example.task_manager.entity.event.Event;
import com.example.task_manager.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthorizeAccessController {
    //TODO wykonać aby body przyjmowało token i object
    //wykorzystać Map<Object key, Object value>
    // "holder": {
    //        "token": "ASDGDFQWEE",
    //        "name": "spotkanie",
    //        "description": "dadasdasdas dasdasdasdas ,dasdasdasdasd .dasdasdasdasd"
    //    }
    //holder.get("token") (== "test one")?-nie wiadomo czy potrzebne

    private final AuthService authService;

    public AuthorizeAccessController(AuthService authService) {
        this.authService = authService;
    }

    // User controller

    @GetMapping("/user/login/")
    public String login(String email, String password) {
        return authService.login(email, password);
    }

    @DeleteMapping("/delete/user/{id}")
    public void deleteUser(String token, @PathVariable("id") Long idUserToDelete) {
        authService.deleteUser(idUserToDelete,token);
    }
    // Event controller


    @PostMapping("/event/create")
    public ResponseEntity<String> saveEvent(@RequestBody Map<String, String> json){
        String token = json.get("token");
//        String[] eventJsonSplit = json.get("event").split("\",");
        Event event = new Event(json.get("name"), json.get("description"));
        return authService.saveEvent(event);
    }
    @GetMapping("/event/get/")
    public Event getByIdEvent(String token, Long id){
        return authService.getByIdEvent(token,id);
    }
    @GetMapping("/event/get/all")
    public List<Event> getAllEvents(String token){
        return authService.getAllEvents();
    }

    @DeleteMapping("/event/delete/{token}/{id}")
    public void deleteEvent(@PathVariable("token") String token, @PathVariable("id") Long idUserToDelete){
        authService.deleteEvent(idUserToDelete,token);
    }

}
