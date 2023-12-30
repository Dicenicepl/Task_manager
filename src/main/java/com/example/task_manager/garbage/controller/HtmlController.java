package com.example.task_manager.garbage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/user/get/")
    public String getUserByEmail(){
        return "profile";
    }
    @GetMapping("/user/get/all")
    public String getAllUsers(){
        return "listusers";
    }
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @GetMapping("/event/create")
    public String createEvent(){
        return "createevent";
    }
    @GetMapping("/event/get")
    public String getEventById(){
        return "event";
    }
    @GetMapping("/event/get/all")
    public String getAllEvents(){
        return "listevents";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
