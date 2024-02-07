package com.encore.logeat.mail.controller;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.common.redis.RedisService;
import com.encore.logeat.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    private final EmailService emailService;
    private final RedisService redisService;

    @Autowired
    public MailController(EmailService emailService, RedisService redisService) {
        this.emailService = emailService;
        this.redisService = redisService;
    }

    @PostMapping("/emails/verifications")
    public ResponseEntity<ResponseDto> verificationEmail(@RequestParam("email") String email,
                                                         @RequestParam("authCode") String authCode) {

        String authValues = redisService.getValues(email);
        boolean isAuthCheck = redisService.checkExistsValue(authValues);
        String resultMessage = "";

        if(isAuthCheck) {
            resultMessage = "이메일 인증이 완료되었습니다.";
        }else {
            resultMessage = "이메일 인증을 다시 해주세요.";
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, resultMessage, email));
    }



}
