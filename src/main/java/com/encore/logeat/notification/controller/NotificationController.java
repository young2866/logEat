package com.encore.logeat.notification.controller;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.notification.service.NotificationService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@DeleteMapping("/notification/{id}")
	public ResponseEntity<ResponseDto> deleteNoti(@PathVariable Long id) {
		ResponseDto responseDto = notificationService.deleteNoti(id);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
}