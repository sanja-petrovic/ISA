package com.example.isa.util;

import com.example.isa.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSender {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public String send(Email email) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setFrom(sender);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getRecipient()));
            message.setContent(email.getBody(), "text/html; charset=utf-8");
            javaMailSender.send(message);
            return "Mail sent successfully...";
        } catch (Exception e) {
            return "Error while sending mail:" + e.getLocalizedMessage();
        }
    }

}
