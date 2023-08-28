package com.example.task_manager.controller;

import com.example.task_manager.entity.event.EventRepository;
import com.example.task_manager.entity.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HtmlController {
    private UserRepository userRepository;
    private EventRepository eventRepository;


    @GetMapping("/user/get/{id}")
    public String getUserById(@PathVariable Long id, Model model){
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
