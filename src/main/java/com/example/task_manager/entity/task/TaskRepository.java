package com.example.task_manager.entity.task;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Boolean existsEventByName(String name);

    Task findEventsByName(String name);

    void deleteByName(String name);

    // @Query("SELECT * FROM Event WHERE u.date = date")
    // List<Event> findEventToRemind(Date date);

    List<Task> findEventByDate(Date date);
}
