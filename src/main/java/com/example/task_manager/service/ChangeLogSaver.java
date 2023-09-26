package com.example.task_manager.service;

import com.example.task_manager.entity.event.Event;
import com.example.task_manager.entity.log.LogRepository;

public class ChangeLogSaver {
    private final LogRepository logRepository;

    public ChangeLogSaver(LogRepository logRepository) {
        this.logRepository = logRepository;
    }
    public void activeLogProcedures(Event event){

    }
}
