package com.encore.logeat.notification.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationType {
	POST("새로운 기록이 작성되었습니다!"), FOLLOW("새로운 팔로워가 있어요!");

	private final String message;

	public String getMessage() {
		return message;
	}
}
