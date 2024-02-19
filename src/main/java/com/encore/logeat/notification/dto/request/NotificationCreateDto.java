package com.encore.logeat.notification.dto.request;


import com.encore.logeat.notification.domain.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NotificationCreateDto {
	private Long sender_id;
	private String url_path;
	private NotificationType notificationType;

}