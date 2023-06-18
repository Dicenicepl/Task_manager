package com.example.task_manager.event;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public void getById(Long id){
        eventService.getById(id);
    }
    @GetMapping
    public void getAll(){
        eventService.getAll();
    }
    @PostMapping
    public void save(@RequestBody Event event){
        eventService.save(event);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        eventService.delete(id);
    }

}
