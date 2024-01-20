package com.example.task_manager.tasks.services;

import com.example.task_manager.tasks.entities.ProtectedTaskDTO;
import com.example.task_manager.tasks.entities.Task;
import com.example.task_manager.tasks.repositories.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private ProtectedTaskDTO convertTaskToProtectedTaskData(Task task){
        return new ProtectedTaskDTO(
                task.getOwner_email(),
                task.getName(),
                task.getDescription(),
                new Time(task.getCreatingTimeInMinis()),
                new Date(task.getCreatingTimeInMinis()),
                new Time(task.getEndingTimeInMinis()),
                new Date(task.getEndingTimeInMinis()),
                task.getProject());
    }

    public ResponseEntity<List<ProtectedTaskDTO>> getAllTasks(){
        List<Task> tasks = taskRepository.findAll();
        List<ProtectedTaskDTO> protectedTaskData = new ArrayList<>();
        for (Task task : tasks){
            protectedTaskData.add(convertTaskToProtectedTaskData(task));
        }
        return new ResponseEntity<>(protectedTaskData, HttpStatus.OK);
    }

    public ResponseEntity<ProtectedTaskDTO> getTaskByName(String name) {
        Task task = taskRepository.findTaskByName(name);
        return new ResponseEntity<>(convertTaskToProtectedTaskData(task), HttpStatus.OK);
    }

    public ResponseEntity<String> createTask(Task task) {
        return null;
    }

    public ResponseEntity<String> updateTask(Task task) {
        return null;
    }

    public ResponseEntity<String> deleteTask(Task task) {
        return null;
    }
}
