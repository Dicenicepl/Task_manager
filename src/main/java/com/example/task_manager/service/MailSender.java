package com.example.task_manager.service;

import com.example.task_manager.config.apps.mailsender.AppConfig;
import com.example.task_manager.entity.event.EventRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;

@Service
// TODO: 08.11.2023 after entity change of 'Event' add finding events by closest time and send 'owner_email' message 
public class MailSender {
    
    private final EventRepository eventRepository;
    AppConfig appConfig = new AppConfig();
    SimpleMailMessage message = new SimpleMailMessage();

    public MailSender(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void sendEmail() throws GeneralSecurityException {
        message.setFrom("sasza.krolik@interia.pl");
        message.setTo("sasza.krolik@interia.pl");
        message.setSubject("test");
        message.setText("1234");
        appConfig.getJavaMailSender().send(message);
    }
    private void findUserData(){

    }
}
