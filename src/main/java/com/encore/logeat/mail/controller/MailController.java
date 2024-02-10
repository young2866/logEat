package com.encore.logeat.mail.controller;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.common.redis.RedisService;
import com.encore.logeat.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.*;

@RestController
public class MailController {

    private final EmailService emailService;
    private final RedisService redisService;

    @Autowired
    public MailController(EmailService emailService, RedisService redisService) {
        this.emailService = emailService;
        this.redisService = redisService;
    }

    @PostMapping("/emails/auth/verifications")
    public ResponseEntity<?> verificationEmail(@RequestParam("email") String email,
                                               @RequestParam("authCode") String authCode) {
        String resultMessage = "";

        String authValues = redisService.getValues(email);
        boolean isAuthCheck = redisService.checkExistsValue(authValues);
        boolean isAuthEquals = authCode.equals(authValues);

        if(isAuthCheck) {
            if(isAuthEquals) {
                resultMessage = "이메일 인증이 완료되었습니다.";
            }else {
                resultMessage = "인증번호가 맞지않습니다.\n 다시 확인해주세요.";
            }
        }else {
            resultMessage = "이메일 인증시간이 만료되었습니다";
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, resultMessage, email));
    }

    @PostMapping("/email/auth/")
    public ResponseEntity<?> authMailSend(@RequestParam String email) {
        ResponseEntity<?> response;
        try {
            String authNumber = emailService.generateRandomNumber();
            emailService.createEmailAuthNumber(email, authNumber);
        }catch (MailException e) {
            throw new MailSendException("이메일 인증코드 전송에 실패했습니다.");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "이메일로 인증코드가 발송되었습니다", email));
    }



}
