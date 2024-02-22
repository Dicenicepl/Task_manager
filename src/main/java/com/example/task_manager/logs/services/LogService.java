package com.example.task_manager.logs.services;

import com.example.task_manager.logs.entities.Log;
import com.example.task_manager.logs.repositories.LogRepository;
import com.example.task_manager.tokens.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogService {
    private final LogRepository logRepository;
    private final TokenService tokenService;

    public LogService(LogRepository logRepository, TokenService tokenService) {
        this.logRepository = logRepository;
        this.tokenService = tokenService;
    }

    private Log creatingFirstConfiguration(Object entity, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String tokenOwner = tokenService.findAssignedEmailByToken(token);
        return new Log(entity.toString(), request.getMethod(), tokenOwner, request.getRequestURL().toString());
    }
//    public void failedRequest(){
//        Log succ = creatingFirstConfiguration();
//        succ.setSucceed(false);
//        logRepository.save(succ);
//    }
    public void succeedRequest(Object entity, HttpServletRequest request){
        Log fail = creatingFirstConfiguration(entity, request);
        fail.setSucceed(true);
        logRepository.save(fail);
    }

}
