package com.example.task_manager.controller;

import com.example.task_manager.entity.AppCalendary;
import com.example.task_manager.entity.AppUser;
import com.example.task_manager.service.AppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @PostMapping("/create/event")
    public ResponseEntity<String> saveEventToDatabase(@RequestBody AppCalendary appCalendary){
        return appService.saveEventToDatabase(appCalendary);
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<AppUser>> showAllUsers(){
        return new ResponseEntity<>(appService.showAllUsers(), HttpStatus.OK);
    }
    @GetMapping("/profile")
    public ResponseEntity<AppUser> showProfileByEmail(String email){
        return new ResponseEntity<>(appService.showProfileByEmail(email), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable("email") String email){
        if (appService.deleteUser(email)){
            return new ResponseEntity<>("User deleted!", HttpStatus.OK);
        } else return new ResponseEntity<>("User not found", HttpStatus.GONE);
    }
    @PostMapping("/create/user")
    public ResponseEntity<String> saveUserToDatabase(@RequestBody AppUser appUser){
        return appService.saveUserToDatabase(appUser);
    }
}
