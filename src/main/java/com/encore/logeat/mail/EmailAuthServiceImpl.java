package com.encore.logeat.mail;

import java.time.Duration;

public class EmailAuthServiceImpl implements EmailAuthService {

    private final EmailAuthService emailAuthService;

    public EmailAuthServiceImpl(EmailAuthService emailAuthService) {
        this.emailAuthService = emailAuthService;
    }

    @Override
    public String createEmailAuthNumber(String email, String authNumber) {
        return authNumber;
    }
}
