package com.example.isa.util;

import com.example.isa.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

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

    @Async
    public String sendWithImage(Email email, byte[] imageData) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setFrom(sender);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getRecipient()));
            
            Multipart mp = new MimeMultipart();
            
            MimeBodyPart bodyPart1 = new MimeBodyPart();
            bodyPart1.setHeader("Content-Type", "text/html; charset=utf-8");
            bodyPart1.setContent(email.getBody(), "text/html");
            
            mp.addBodyPart(bodyPart1);
            
            ByteArrayDataSource bds = new ByteArrayDataSource(imageData, "image/png"); 
            MimeBodyPart messageBodyPart = new MimeBodyPart();
        	messageBodyPart.setDataHandler(new DataHandler(bds)); 
        	messageBodyPart.setFileName("./phill.png");
        	messageBodyPart.setHeader("Content-ID", "<image>");
        	mp.addBodyPart(messageBodyPart);
            
        	message.setContent(mp);
        	
            javaMailSender.send(message);
            return "Mail sent successfully...";
        } catch (Exception e) {
            return "Error while sending mail:" + e.getLocalizedMessage();
        }
    }
}
