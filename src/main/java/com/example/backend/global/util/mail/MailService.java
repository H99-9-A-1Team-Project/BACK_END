package com.example.backend.global.util.mail;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class MailService {

    private JavaMailSender emailSender;

    @Async("asyncTaskExecutor")
    public void sendSimpleMessage(MailDto mailDto, String htmlTemplate) throws MessagingException {

        HashMap<String, String> emailValues = new HashMap<>();
        emailValues.put("name", mailDto.getAddress());

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject(mailDto.getTitle());
        helper.setTo(mailDto.getAddress());
        helper.setFrom("liighthouse-keeper@gmail.com");
        helper.setText(htmlTemplate, true);

        emailSender.send(message);
    }
}