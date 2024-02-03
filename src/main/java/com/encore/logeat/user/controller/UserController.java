package com.encore.logeat.user.controller;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.dto.request.UserCreateRequestDto;
import com.encore.logeat.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user/new")
	public ResponseEntity<ResponseDto> createUser(UserCreateRequestDto userCreateRequestDto) {
		User user = userService.createUser(userCreateRequestDto);
		return new ResponseEntity<>(
			new ResponseDto(HttpStatus.CREATED, "new User Created!", user.getId()),
			HttpStatus.CREATED);
	}

}