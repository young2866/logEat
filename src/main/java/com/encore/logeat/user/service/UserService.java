package com.encore.logeat.user.service;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.common.jwt.JwtTokenProvider;
import com.encore.logeat.common.redis.RedisService;
import com.encore.logeat.mail.service.EmailService;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.dto.request.UserCreateRequestDto;
import com.encore.logeat.user.dto.request.UserLoginRequestDto;
import com.encore.logeat.user.repository.UserRepository;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final EmailService emailService;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider, EmailService emailService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
        this.emailService = emailService;
    }

	@Transactional
	public User createUser(UserCreateRequestDto userCreateRequestDto) {
		emailService.createEmailAuthNumber(userCreateRequestDto.getEmail());

		userCreateRequestDto.setPassword(
			passwordEncoder.encode(userCreateRequestDto.getPassword()));
		User user = userCreateRequestDto.toEntity();
		// 유저 프로필 이미지 추가해주는 로직 작성 필요
		return userRepository.save(user);
	}

	@Transactional
	public ResponseDto userLogin(UserLoginRequestDto userLoginRequestDto) {
		User user = userRepository.findByEmail(userLoginRequestDto.getEmail())
			.filter(
				it -> passwordEncoder.matches(userLoginRequestDto.getPassword(), it.getPassword()))
			.orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));
		String token = jwtTokenProvider.createToken(
			String.format("%s:%s", user.getId(), user.getRole()));
		return new ResponseDto(HttpStatus.OK, "JWT token is created!", token);
	}


	@Transactional
	public ResponseEntity<?> updatePassword(String emailAuthNumber, String email, String changePwd) {
		Boolean b = emailService.verificationEmailAuth(email, emailAuthNumber);

		User findUser = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("아이디가 없습니다."));
		String encode = passwordEncoder.encode(changePwd);

		String message = "";
		if(b) {
			findUser.updatedPassword(encode);
			message = "비밀번호가 변경되었습니다.";
		}else {
			message = "인증이 만료되었습니다. 다시 설정해주시길 바랍니다.";
		}

		return ResponseEntity.ok()
				.body(new ResponseDto(HttpStatus.OK, message, findUser.getEmail()));
	}




}