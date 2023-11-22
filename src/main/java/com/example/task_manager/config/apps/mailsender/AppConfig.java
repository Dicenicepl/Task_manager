package com.example.task_manager.config.apps.mailsender;

import com.sun.mail.util.MailSSLSocketFactory;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.security.GeneralSecurityException;
import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class AppConfig {
    @Bean
    public JavaMailSender getJavaMailSender() throws GeneralSecurityException {
        Dotenv data = Dotenv.configure().load(); //env file that containt data
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        MailSSLSocketFactory sf = new MailSSLSocketFactory();

        sf.setTrustAllHosts(true);
        mailSender.setHost(data.get("HOST"));
        mailSender.setPort(Integer.parseInt(data.get("PORT")));
        mailSender.setUsername(data.get("USERNAME"));
        mailSender.setPassword(data.get("PASSWORD"));
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
