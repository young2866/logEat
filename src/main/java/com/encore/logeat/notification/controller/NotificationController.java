package com.encore.logeat.notification.controller;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

	private final NotificationService notificationService;

	@Autowired
	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@GetMapping("/user/notifications")
	public ResponseEntity<ResponseDto> myAllNoti() {
		ResponseDto responseDto = notificationService.getAllMyNoti();
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
}