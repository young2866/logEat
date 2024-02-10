package com.encore.logeat.mail.service;

import com.encore.logeat.common.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisService redisService;
    private final MailProperties mailProperties;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, RedisService redisService, MailProperties mailProperties) {
        this.javaMailSender = javaMailSender;
        this.redisService = redisService;
        this.mailProperties = mailProperties;
    }

    @Async
    public void sendEmail(String from, String to, String title, String contents) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(title);
        mailMessage.setText(contents);

        javaMailSender.send(mailMessage);
    }


    @Async
    public String createEmailAuthNumber(String email, String authNumber) {
        Duration duration = Duration.ofMinutes(3);
        redisService.setValues(email, authNumber, duration);

        sendEmail(mailProperties.getUsername(), email, "인증번호", authNumber);

        return authNumber;
    }

    public Boolean verificationEmailAuth(String email, String authNumber) {
        String redisAuthNum = redisService.getValues(email);
        boolean isAuthNumberCheck = redisService.checkExistsValue(redisAuthNum);

        return isAuthNumberCheck;
    }



    private boolean isCheck(String check) {
        return check == null || check.isEmpty();
    }

    public String generateRandomNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }




}
