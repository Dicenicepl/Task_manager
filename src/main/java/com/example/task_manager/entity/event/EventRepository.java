package com.example.task_manager.entity.event;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Boolean existsEventByName(String name);

    Event findEventsByName(String name);

    void deleteByName(String name);

    // @Query("SELECT * FROM Event WHERE u.date = date")
    // List<Event> findEventToRemind(Date date);

    List<Event> findEventByDate(Date date);
}
