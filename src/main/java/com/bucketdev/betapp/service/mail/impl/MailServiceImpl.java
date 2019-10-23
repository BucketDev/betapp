package com.bucketdev.betapp.service.mail.impl;

import com.bucketdev.betapp.dto.user.UserDTO;
import com.bucketdev.betapp.service.mail.MailService;
import com.bucketdev.betapp.type.MailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Async
    public void sendEmail(MailType type, List<UserDTO> users) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(users.stream().map(UserDTO::getEmail).toArray(String[]::new));
        mailMessage.setSubject("test");
        mailMessage.setText("test message");

        mailSender.send(mailMessage);
    }

}
