package com.example.task_manager.event;

import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void getById(Long id) {
        eventRepository.findById(id);
    }

    public void getAll() {
        eventRepository.findAll();
    }

    public void save(Event event) {
        eventRepository.save(event);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
