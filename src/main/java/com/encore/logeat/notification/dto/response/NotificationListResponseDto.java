package com.encore.logeat.notification.dto.response;

import com.encore.logeat.notification.domain.Notification;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationListResponseDto {

	private Long id;
	private String senderName;
	private String senderProfileImageUrl;
	private String message;

	public static NotificationListResponseDto toDto(Notification notification) {
		return NotificationListResponseDto.builder()
			.id(notification.getId())
			.senderName(notification.getSenderName())
			.senderProfileImageUrl(notification.getSender_profile_image())
			.message(notification.getNotificationType().getMessage()).build();
	}

}