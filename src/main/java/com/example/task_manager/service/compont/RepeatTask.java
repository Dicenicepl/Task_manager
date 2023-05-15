package com.example.task_manager.service.compont;

import com.example.task_manager.config.AppConfig;
import com.example.task_manager.repository.CalendaryRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.security.GeneralSecurityException;

@Configuration
@EnableScheduling
public class RepeatTask {
    private final CalendaryRepository calendaryRepository;

    public RepeatTask(CalendaryRepository calendaryRepository) {
        this.calendaryRepository = calendaryRepository;
    }

    @Scheduled(fixedDelay = 10000)
    public void settingRepeater() {
        try {
            MailReminder mailReminder = new MailReminder(new AppConfig().getJavaMailSender(), calendaryRepository);
            mailReminder.notificationSender();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }catch (IllegalArgumentException e) {
            System.out.println("ERROR: DATE TO SEND EMAIL IS NEGATIVE");
            throw new IllegalArgumentException(e);
        }
    }
}
