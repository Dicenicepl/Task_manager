package com.example.task_manager.controller;

import com.example.task_manager.entity.AppCalendary;
import com.example.task_manager.entity.AppUser;
import com.example.task_manager.service.AppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.List;

@RestController
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @PostMapping("/createevent")
    public void saveEventToDatabase(@RequestBody AppCalendary appCalendary){
        AppCalendary converterToData = new AppCalendary(
                appCalendary.getDate(),
                appCalendary.getEventName(),
                appCalendary.getEventDescription(),
                appCalendary.getEventOwnerEmail(),
                appCalendary.getEventGuestsEmails());
        appService.saveEventToDatabase(converterToData);
    }
    @GetMapping("/allusers")
    public ResponseEntity<List<AppUser>> showAllUsers(){
        return new ResponseEntity<>(appService.showAllUsers(), HttpStatus.OK);
    }
    @GetMapping("/runSender")
    public void runSender() throws GeneralSecurityException {
        appService.runSender();
    }
    @GetMapping("/profile")
    public AppUser showProfileByEmail(String email){
        return appService.showProfileByEmail(email);
    }
    @DeleteMapping("/delete/{email}")
    public void deleteUser(@PathVariable("email") String email){
        appService.deleteUser(email);
    }
    @PostMapping("/createuser")
    public void saveUserToDatabase(@RequestBody AppUser appUser){
        appService.saveUserToDatabase(new AppUser(
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getNickName(),
                appUser.getEmail(),
                appUser.getPassword(),
                appUser.getRole())
        );
    }

}
