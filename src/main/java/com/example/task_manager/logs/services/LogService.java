package com.example.task_manager.logs.services;

import com.example.task_manager.tokens.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogService {
    private final TokenService tokenService;
    private final HttpServletRequest request;
    private final Object entity;

    public LogService(TokenService tokenService, Object entity, HttpServletRequest request) {
        this.tokenService = tokenService;
        this.entity = entity;
        this.request = request;
    }

    public void getOperation() {
    }

    public void setOperation() {
    }

    public void putOperation() {
    }

    public void deleteOperation() {
    }

}
