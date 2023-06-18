package com.example.task_manager.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<String> getById(Long id){
        return userService.getById(id);
    }
    @GetMapping
    public ResponseEntity<String> getAll(){
        return userService.getAll();
    }
    @PostMapping()
    public ResponseEntity<String> save(@RequestBody User user){
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        userService.delete(id);
    }
}
