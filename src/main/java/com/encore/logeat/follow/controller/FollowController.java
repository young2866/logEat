package com.encore.logeat.follow.controller;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.follow.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowController {

	private final FollowService followService;

	@Autowired
	public FollowController(FollowService followService) {
		this.followService = followService;
	}

	@PostMapping("/follow/{userId}")
	public ResponseEntity<ResponseDto> addFollow(@PathVariable Long userId) {
		ResponseDto responseDto = followService.addFollow(userId);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
}