package com.example.task_manager.garbage.controller;

import com.example.task_manager.garbage.entity.AddTaskObject;
import com.example.task_manager.garbage.entity.task.TaskDTO;
import com.example.task_manager.garbage.service.AuthService;
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
    
    /*
    json{
        email:
        password:
    }
     */
    @PostMapping("/user/login/")
    public ResponseEntity<String> login(@RequestBody Map<String, String> json) {
        return authService.login(json);
    }
    /*
    localhost:PORT/auth/user/logout/?email= &token= 
     */
    @GetMapping("/user/logout/")
    public void logout(String email, String token){
        authService.logout(email, token);
    }
    /*
    json{
        email:
        token:
    }
     */
    @DeleteMapping("/user/delete/")
    public ResponseEntity<String> deleteUser(@RequestBody Map<String, String> json) {
        return authService.deleteUser(json);
    }
    /*
    json{
        username:
        email:
        password:
        token:
    }
     */
    @PutMapping("/user/update/")
    public ResponseEntity<String> updateUser(@RequestBody Map<String, String> json){
        return authService.updateUser(json);
    }

    /*
    json{
        token:
        owner_email:
        name:
        description:
    }
     */
    @PostMapping("/event/create/")
    public ResponseEntity<String> saveEvent(@RequestBody Map<String, String> json) {
        return authService.saveTask(json);
    }

    /*
        token:
     */
    @GetMapping("/event/get/all/")
    public List<TaskDTO> getAllEvents(@RequestBody String token) {
        return authService.getAllEvents(token);
    }
    /*
        localhost:PORT/auth/event/delete/?name=
        token:
     */
    @DeleteMapping("/event/delete/")
    public ResponseEntity<String> deleteEvent(String name,
                                              @RequestBody String token) {
        return authService.deleteEvent(name, token);
    }
    /*
    json{
        name:
        token:
        description:
    }
     */
//    @PutMapping("/event/update/")
//    public ResponseEntity<String> updateEvent(@RequestBody Map<String, String> json){
//        return authService.updateEvent(json);
//    }
    /*
    json{
        token:
        name:
        description:
    }
     */
    @PostMapping("/project/create/")
    public ResponseEntity<String> createProject(@RequestBody Map<String, String> json){
        return authService.createProject(json);
    }
    /*
    json{
        token:
        name:
        description:
    }
   */
    @PutMapping("/project/addTasks/")
    public ResponseEntity<String> addTasksToProject(@RequestBody AddTaskObject addTaskObject){
        return authService.addTasksToProject(addTaskObject);
    }


    @PutMapping("/project/update/")
    public ResponseEntity<String> updateProject(@RequestBody Map<String, String> json){
        return authService.updateProject(json);
    }

}
