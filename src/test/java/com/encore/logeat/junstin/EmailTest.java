package com.encore.logeat.junstin;


import com.encore.logeat.common.redis.RedisService;
import com.encore.logeat.mail.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailTest {


    @Autowired
    private RedisService redisService = new RedisService(new RedisTemplate<>());

    @Autowired
    private EmailService emailService;

    @Autowired
    private MailProperties mailProperties;

    @Test
    @DisplayName("이메일 전송 테스트")
    public void 이메일테스트() {
        String to = "junstin119@gmail.com";
        String title = "LogEat 전송 테스트";
        String contents = "인증번호1212131212";

        emailService.sendEmail(mailProperties.getUsername(), to, title, contents);
    }




}
