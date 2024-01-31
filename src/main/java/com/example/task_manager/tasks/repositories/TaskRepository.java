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

    @Query(value = "SELECT t.task_id, assigned_to, creating_time_in_minis, description, ending_time_in_minis, name, owner_email, status, project_id FROM tasks t WHERE t.project_id = :id", nativeQuery = true)
//    @Query(value = "SELECT *FROM tasks t WHERE t.project_id = :id", nativeQuery = true)
    Set<Task> findTaskByProject(@Param("id") int id);
}
