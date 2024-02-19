package com.encore.logeat.notification.dto.response;

import com.encore.logeat.notification.domain.Notification;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationListResponseDto {

	private String senderName;
	private String url_path;
	private String message;

	public static NotificationListResponseDto toDto(Notification notification) {
		return NotificationListResponseDto.builder()
			.senderName(notification.getSenderName())
			.url_path(notification.getUrl_path())
			.message(notification.getNotificationType().getMessage()).build();
	}
}