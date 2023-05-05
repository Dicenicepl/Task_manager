package com.example.task_manager.config;

import com.example.task_manager.config.passwords.EmailData;
import com.sun.mail.util.MailSSLSocketFactory;
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
    private EmailData emailData = new EmailData();
    @Bean
    public JavaMailSender getJavaMailSender() throws GeneralSecurityException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        mailSender.setHost(emailData.host);
        mailSender.setPort(emailData.port);
        mailSender.setUsername(emailData.email);
        mailSender.setPassword(emailData.password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "poczta.interia.pl");

        return mailSender;
    }
}
