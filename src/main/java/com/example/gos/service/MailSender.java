package com.example.gos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;


    public void send(String emailTo, String subject, String buildings) {
        SimpleMailMessage mailbuildings = new SimpleMailMessage();

        mailbuildings.setFrom(username);
        mailbuildings.setTo(emailTo);
        mailbuildings.setSubject(subject);
        mailbuildings.setText(buildings);

        mailSender.send(mailbuildings);
    }
}