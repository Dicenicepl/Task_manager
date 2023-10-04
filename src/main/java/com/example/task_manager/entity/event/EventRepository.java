package com.example.task_manager.entity.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Boolean existsEventByName(String name);
    Event findEventsByName(String name);

    void deleteByName(String name);
}
