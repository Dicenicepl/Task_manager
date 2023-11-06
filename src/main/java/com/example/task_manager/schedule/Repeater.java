package com.example.task_manager.schedule;

import com.example.task_manager.config.apps.mailsender.AppConfig;
import com.example.task_manager.config.apps.mailsender.EmailSender;
import com.example.task_manager.entity.token.Token;
import com.example.task_manager.entity.token.TokenRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Time;
import java.util.List;

@Configuration
@EnableScheduling
public class Repeater {

    private final TokenRepository tokenRepository;

    public Repeater(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    // TODO: 05.11.2023 sprawdź czemu wywala się scheduled, problem tkwi w skomentowanej linijce
    @Scheduled(fixedDelay = 1000L)
    void checkExpireToken(){
        try{
            System.out.println("!!!!!!!!!!DASDASD");
            List<Token> tokens = tokenRepository.findAllByExpireTimeBefore(new Time(System.currentTimeMillis()));
            try {
                for (Token token : tokens) {
                    if (!token.getGeneratedToken().equals("AAAAAAAAAA")) {
                        tokenRepository.updateTokenToNull(token.getGeneratedToken());
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("CHECKEXPIRETOKEN: NULL");
            }
//            AppConfig config = new AppConfig();
//
//            EmailSender emailSender = new EmailSender(config.getJavaMailSender());
//
//            emailSender.sendEmail();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
