package com.encore.logeat.junstin;

import com.encore.logeat.common.redis.RedisService;
import com.encore.logeat.mail.service.EmailService;
import com.encore.logeat.user.domain.Role;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.dto.request.UserCreateRequestDto;
import com.encore.logeat.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class EmailServiceTest {


    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MailProperties mailProperties;

    @Test
    @DisplayName("회원가입후 이메일 인증")
    public void 이메일인증() {

        var userCreateRequestDto = new UserCreateRequestDto();
        userCreateRequestDto.setEmail("psjun727@naver.com");
        userCreateRequestDto.setNickname("junstin");
        userCreateRequestDto.setPassword("12345");
        userCreateRequestDto.setIntroduce("자기소개");
        userCreateRequestDto.setProfileImage(null);

        userService.createUser(userCreateRequestDto);

        String authNumber = emailService.generateRandomNumber();
        Duration duration = Duration.ofMinutes(3);

        emailService.createEmailAuthNumber(userCreateRequestDto.getEmail(), authNumber);
        redisService.setValues(userCreateRequestDto.getEmail(), authNumber, duration);

        String authValues = redisService.getValues(userCreateRequestDto.getEmail());
        boolean isAuthCheck = redisService.checkExistsValue(authValues);

        Assertions.assertThat(isAuthCheck).isEqualTo(true);
    }



}