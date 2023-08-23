package com.example.task_manager.controller;

import com.example.task_manager.entity.user.User;
import com.example.task_manager.service.NoAuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class NoAuthorizeAccessController {
    private final NoAuthService noAuthService;

    public NoAuthorizeAccessController(NoAuthService noAuthService) {
        this.noAuthService = noAuthService;
    }

    @RequestMapping(
            path = "/user/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {
                    MediaType.APPLICATION_ATOM_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    public ResponseEntity<String> register(User user){
        return noAuthService.register(user);
    }

    @GetMapping("/user/get/")
    public ResponseEntity<String> getById(Long id){
        return noAuthService.getById(id);
    }

    @GetMapping("/user/get/all")
    public ResponseEntity<String> getAll(){
        return noAuthService.getAll();
    }
}
