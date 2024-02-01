package com.example.task_manager.tasks.repositories;

import com.example.task_manager.tasks.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findTaskByName(String name);

    @Query("SELECT t FROM Task t WHERE t.project.id = :id")
    Set<Task> findTaskByProject(@Param("id") Long id);

}
