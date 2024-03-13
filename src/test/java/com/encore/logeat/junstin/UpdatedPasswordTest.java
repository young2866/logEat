package com.encore.logeat.junstin;

import com.encore.logeat.mail.service.EmailService;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.dto.request.UserCreateRequestDto;
import com.encore.logeat.user.repository.UserRepository;
import com.encore.logeat.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
public class UpdatedPasswordTest {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호 변경 테스트")
    public void 비밀번호변경() {

    }


}
