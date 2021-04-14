package com.example.services;

import com.example.models.EmailSender;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailSenderService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailSenderService.class);
    private final JavaMailSender javaMailSender;


    @Async
    @Override
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            if (email.contains("password"))
                helper.setSubject("Change password confirmation");
            else
                helper.setSubject("Confirm your email");
            helper.setFrom("bureaucracyhacks@gmail.com");
            helper.setText(email, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException(/*"{\"message\" : \*/"failed to send email.");
        }
    }
}
