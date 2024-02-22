package com.encore.logeat.notification.service;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.follow.domain.Follow;
import com.encore.logeat.notification.domain.Notification;
import com.encore.logeat.notification.dto.request.NotificationCreateDto;
import com.encore.logeat.notification.dto.response.NotificationListResponseDto;
import com.encore.logeat.notification.repository.NotificationRepository;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final UserRepository userRepository;

	@Autowired
	public NotificationService(NotificationRepository notificationRepository,
		UserRepository userRepository) {
		this.notificationRepository = notificationRepository;
		this.userRepository = userRepository;
	}

	public void createNotification(NotificationCreateDto notificationCreateDto) {
		User sender = userRepository.findById(notificationCreateDto.getSender_id())
			.orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));
		User provider = userRepository.findById(notificationCreateDto.getFollowing_id())
			.orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));

		Notification notification = Notification.builder()
			.notificationType(notificationCreateDto.getNotificationType())
			.senderName(sender.getNickname())
			.sender_profile_image(notificationCreateDto.getSender_profile_url())
			.provider(provider)
			.build();
		notificationRepository.save(notification);
	}

	@PreAuthorize("hasAuthority('USER')")
	public ResponseDto getAllMyNoti() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		String[] split = name.split(":");
		long userId = Long.parseLong(split[0]);

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("예상치 못한 에러가 발생하였습니다."));
		List<NotificationListResponseDto> notifications = new ArrayList();
		for (Notification notification : user.getNotifications()) {
			notifications.add(
				NotificationListResponseDto.toDto(notification));
		}
		return new ResponseDto(HttpStatus.OK, "All My Notifications", notifications);
	}

	@PreAuthorize("hasAuthority('USER')")
	public ResponseDto deleteNoti(Long id) {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		long userId = Long.parseLong(name);

		Notification notification = notificationRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("알림 삭제 실패"));
		if (notification.getProvider().getId() != userId) {
			throw new IllegalArgumentException("잘못된 요청입니다.");
		}
		notificationRepository.delete(notification);
		return new ResponseDto(HttpStatus.OK, "알림이 삭제되었습니다.", null);
	}
}