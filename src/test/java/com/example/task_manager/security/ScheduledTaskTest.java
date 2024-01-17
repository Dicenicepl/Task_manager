package com.example.task_manager.security;


import com.example.task_manager.security.configuration.ClearAutorization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ScheduledTaskTest {

    @Autowired
    private ClearAutorization clearAutorization;

    @Test
    void test(){
        clearAutorization.clearTokens();
    }

}
