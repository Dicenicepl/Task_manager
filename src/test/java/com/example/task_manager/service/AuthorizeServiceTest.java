package com.example.task_manager.service;

import com.example.task_manager.garbage.service.AuthService;
import com.example.task_manager.garbage.entity.project.Project;
import com.example.task_manager.garbage.entity.project.ProjectRepository;
import com.example.task_manager.garbage.entity.task.Task;
import com.example.task_manager.garbage.entity.task.TaskRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class AuthorizeServiceTest {
    @Autowired
    AuthService authService;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TaskRepository taskRepository;

    private static final Map<String, String> userMethodsJson = new HashMap<>();
    private static final Map<String, String> eventMethodsJson = new HashMap<>();

    @BeforeAll
    public static void preparing(){

        userMethodsJson.put("email","Dicenicepl@gmail.com");
        userMethodsJson.put("password", "1234");
        userMethodsJson.put("username", "Dicenice");
        userMethodsJson.put("generatedToken", "AAAAAAAAAA");

        eventMethodsJson.put("generatedToken","AAAAAAAAAA");
        eventMethodsJson.put("owner_email","dicenicepl@gmail.com");
        eventMethodsJson.put("name","Project");
        eventMethodsJson.put("description","Koty to sa super lecz psy beda the best");
    }

    @Test
    @DisplayName("Login test")
    public void loginTest(){
        assertTrue(authService.login(userMethodsJson).getStatusCode().isSameCodeAs(HttpStatus.OK));
    }

    @Test
    public void createProject(){
        Project project = new Project();
        Task task = new Task();

        task.setProject(project); // Powiązanie zadania z projektem

        List<Task> projectTasks = new ArrayList<>();
        projectTasks.add(task);

        project.setTasks(projectTasks); // Ustawienie listy zadań w projekcie

        projectRepository.save(project);
        taskRepository.save(task);


    }
}