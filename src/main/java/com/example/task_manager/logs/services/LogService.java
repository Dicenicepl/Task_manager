package com.example.task_manager.logs.services;

import com.example.task_manager.logs.entities.Log;
import com.example.task_manager.logs.repositories.LogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LogService {
    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void creatingFirstConfiguration(HttpServletRequest request, String email) throws IOException {
        Log logging = new Log(
                request.getReader().lines().collect(Collectors.joining(System.lineSeparator())),
                request.getMethod(),
                email,
                request.getRequestURL().toString()
        );
        logRepository.save(logging);
    }
}
