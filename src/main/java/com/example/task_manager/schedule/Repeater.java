package com.example.task_manager.schedule;

import com.example.task_manager.entity.user.User;
import com.example.task_manager.entity.user.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Repeater {
    private UserRepository userRepository;

    @Scheduled(fixedDelay = 10000L)
    void checkExpireToken(){
        User user = userRepository.findUserByExpireToken(System.currentTimeMillis());
    }
}
