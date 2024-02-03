package com.example.task_manager.Task;

import com.example.task_manager.tasks.entities.RegisterTaskDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskService {
    @Autowired
    private com.example.task_manager.tasks.services.TaskService taskService;


    @Test
    @DisplayName("Create User")
    void createTask(){
        assertEquals(ResponseEntity.ok("Project not found"),taskService.createTask(null, null));
        assertEquals(ResponseEntity.badRequest().body(null), taskService.createTask(null, 1L));
        assertEquals(ResponseEntity.badRequest().body(null), taskService.createTask(new RegisterTaskDTO(null, null, null, null, null), 1L));
        assertEquals(ResponseEntity.ok("Created"), taskService.createTask(new RegisterTaskDTO("Dicenice@example.com", "Gaming", "Dicenice@example.com", "Let`s play some ets2", 1706988426354L), 1L));
        assertEquals(ResponseEntity.badRequest().body(null), taskService.createTask(new RegisterTaskDTO("Dicenice@example.com", "Gaming", "Dicenice@example.com", "Let`s play some ets2", 1706988426354L), 1L));

    }
}
