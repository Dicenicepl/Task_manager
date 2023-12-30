package com.example.task_manager.tasks.repositories;

import com.example.task_manager.tasks.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
