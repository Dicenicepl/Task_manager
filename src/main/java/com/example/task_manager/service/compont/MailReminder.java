package com.example.task_manager.service.compont;

import com.example.task_manager.entity.AppCalendary;
import com.example.task_manager.repository.CalendaryRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;
import java.sql.Date;

@Component
public class MailReminder {
    private final JavaMailSender mailSender;
    private final CalendaryRepository calendaryRepository;

    public MailReminder(JavaMailSender mailSender, CalendaryRepository calendaryRepository) {
        this.mailSender = mailSender;
        this.calendaryRepository = calendaryRepository;
    }
    private void sentSimpleMail(String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sasza.krolik@interia.pl");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        System.out.println(message);
        mailSender.send(message);
    }
    public void notificationSender() throws GeneralSecurityException {
        Date todayDate = new Date(System.currentTimeMillis());
        for (AppCalendary event : calendaryRepository.findAllAppCalendariesByDate(todayDate)) {
            if (!event.getIsSended()) {
                sentSimpleMail(event.getEventOwnerEmail(), event.getEventName(), todayDate + " today you have event");
                calendaryRepository.updateIsSended(event.getId(), Boolean.TRUE);
            }
        }
    }
}
