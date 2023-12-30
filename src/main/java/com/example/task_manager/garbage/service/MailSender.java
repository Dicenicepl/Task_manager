package com.example.task_manager.garbage.service;

import com.example.task_manager.garbage.apps.mailsender.AppConfig;
import com.example.task_manager.garbage.entity.task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;

@Service
public class MailSender {
    @Autowired
    TaskRepository taskRepository;

    AppConfig appConfig = new AppConfig();
    SimpleMailMessage message = new SimpleMailMessage();

    public void sendEmail() throws GeneralSecurityException {

        message.setFrom("sasza.krolik@interia.pl");
        message.setTo("sasza.krolik@interia.pl");
        message.setSubject("test");
        message.setText("1234");
        appConfig.getJavaMailSender().send(message);
    }
}
