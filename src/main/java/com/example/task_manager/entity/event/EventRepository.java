package com.example.task_manager.entity.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Boolean existsEventByName(String name);
}
