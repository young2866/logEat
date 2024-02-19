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
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호 변경 테스트")
    public void 비밀번호변경() {
        UserCreateRequestDto createRequestDto = new UserCreateRequestDto();
        createRequestDto.setEmail("aaaa@naver.com");
        createRequestDto.setNickname("aaaa");
        createRequestDto.setPassword("12345");
        createRequestDto.setProfileImage(null);
        createRequestDto.setIntroduce("자기소개");

        User user = userService.createUser(createRequestDto);
        String authNumber = emailService.generateRandomNumber();
        String emailAuthNumber = emailService.createEmailAuthNumber(user.getEmail(), authNumber);

        Boolean b = emailService.verificationEmailAuth(user.getEmail(), emailAuthNumber);

        User findUser = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new EntityNotFoundException("아이디가 없습니다."));
        String changePwd = passwordEncoder.encode("5555");

        if(b) {
            findUser.userUpdatedPassword(changePwd);
        }

        Assertions.assertThat(passwordEncoder.matches("5555", findUser.getPassword())).isEqualTo(true);
    }


}
