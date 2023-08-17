package com.example.task_manager.schedule;

import com.example.task_manager.entity.user.User;
import com.example.task_manager.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Time;
import java.util.List;

@Configuration
@EnableScheduling
public class Repeater {
    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedDelay = 10000L)
    void checkExpireToken(){
        List<User> users = userRepository.findAllByExpireTimeBefore(new Time(System.currentTimeMillis()));
        for (User user : users){
            userRepository.updateTokenToNull(user.getToken());
        }
    }
}
