package com.example.task_manager.config.apps.mailsender;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    private final JavaMailSender mailSender;

    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sasza.krolik@interia.pl");
        message.setTo("sasza.krolik@interia.pl");
        message.setSubject("test");
        message.setText("1234");
        mailSender.send(message);
    }
}
