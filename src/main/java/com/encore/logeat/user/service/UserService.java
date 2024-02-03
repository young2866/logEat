package com.encore.logeat.user.service;

import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.dto.request.UserCreateRequestDto;
import com.encore.logeat.user.repository.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public User createUser(UserCreateRequestDto userCreateRequestDto) {
		userCreateRequestDto.setPassword(passwordEncoder.encode(userCreateRequestDto.getPassword()));
		User user = userCreateRequestDto.toEntity();
		// 유저 프로필 이미지 추가해주는 로직 작성 필요
		return userRepository.save(user);
	}
}