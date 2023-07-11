package com.example.task_manager.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sign")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/in")
    public String login(String email, String password){
        return loginService.login(email,password);
    }
    @GetMapping("/up")
    public String register(String username, String email, String password){
        return loginService.register(username,email,password);
    }
}
