package com.encore.logeat.mail;

import com.encore.logeat.common.redis.RedisService;
import com.encore.logeat.mail.service.EmailService;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailConfig {


//    public EmailService emailService() {
//
//
//    }
//
//
//    @Bean
//    public EmailAuthService emailService(EmailService emailService) {
//        // 생성 패턴
//        // Factory.
//        return new EmailAuthServiceImpl(emailService);
//    }

}
