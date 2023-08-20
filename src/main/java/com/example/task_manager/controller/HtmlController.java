package com.example.task_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HtmlController {
    @GetMapping("/user/get/")
    public String getUserById(){
        return "profile.html";
    }
    @GetMapping("/user/get/all")
    public String getAllUsers(){
        return "listusers.html";
    }
    @PostMapping("/user/register")
    public String register(){
        return "register.html";
    }
    @PostMapping("/event/create")
    public String createEvent(){
        return "createevent.html";
    }
    @GetMapping("/event/get")
    public String getEventById(){
        return "event.html";
    }
    @GetMapping("/event/get/all")
    public String getAllEvents(){
        return "listEvents.html";
    }
    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

}
