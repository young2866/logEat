package com.encore.logeat.mail;

public interface EmailAuthService {
    String createEmailAuthNumber(String email, String authNumber);
}
