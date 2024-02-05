package com.encore.logeat.junstin;


import com.encore.logeat.mail.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailTest {

    @Autowired
    private EmailService emailService;


    @Test
    @DisplayName("이메일 전송 테스트")
    public void 이메일테스트() {

        String from = "junstin119@gmail.com";
        String to = "junstin119@gmail.com";
        String title = "LogEat 전송 테스트";
        String contents = "인증번호1212131212";

        emailService.sendEmail(from, to, title, contents);


    }


}
