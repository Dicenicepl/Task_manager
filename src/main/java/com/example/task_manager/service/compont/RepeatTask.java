package com.example.task_manager.service.compont;

import com.example.task_manager.config.AppConfig;
import com.example.task_manager.repository.CalendaryRepository;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class RepeatTask {
    private MailReminder mailReminder;
    private final CalendaryRepository calendaryRepository;

    public RepeatTask(CalendaryRepository calendaryRepository) {
        this.calendaryRepository = calendaryRepository;
    }
    // TODO: 25.04.2023 wykonać zwrot od metody czy została wykonana, jeżeli tak, wpuścić nowe informacje, jeżeli nie odczekać aż data wykonania będzie równa datie dzisiejszej 

    public void settingRepeater(Date dateToSendEmail) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Sending email started");
                try {
                    mailReminder = new MailReminder(new AppConfig().getJavaMailSender(), calendaryRepository);
                    mailReminder.notificationSender();
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                }catch (IllegalArgumentException e) {
                    System.out.println("ERROR: DATE_TO_SEND_EMAIL IS NEGATIVE");
                    throw new IllegalArgumentException(e);
                }
            }
        };
        Timer timer = new Timer("timer");
        timer.schedule(task, dateToSendEmail/*,period = timebetweenstart*/);
    }
}
