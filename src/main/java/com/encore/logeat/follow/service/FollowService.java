package com.encore.logeat.follow.service;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.follow.domain.Follow;
import com.encore.logeat.follow.repository.FollowRepository;
import com.encore.logeat.notification.domain.NotificationType;
import com.encore.logeat.notification.dto.request.NotificationCreateDto;
import com.encore.logeat.notification.service.NotificationService;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FollowService {

	private final FollowRepository followRepository;
	private final UserRepository userRepository;
	private final NotificationService notificationService;
	@Autowired
	public FollowService(FollowRepository followRepository, UserRepository userRepository,
		NotificationService notificationService) {
		this.followRepository = followRepository;
		this.userRepository = userRepository;
		this.notificationService = notificationService;
	}


	@PreAuthorize("hasAuthority('USER')")
	public ResponseDto addFollow(String nickname) {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		String[] split = name.split(":");
		long userId = Long.parseLong(split[0]);



		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("예기치 못한 에러가 발생하였습니다."));
		if (user.getNickname().equals(nickname)) {
			throw new IllegalArgumentException("나를 팔로우 할 수 없습니다.");
		}
		User followUser = userRepository.findByNickname(nickname)
			.orElseThrow(() -> new EntityNotFoundException("팔로우를 할 유저가 존재하지 않습니다."));
		Optional<Follow> alreadyFollow = followRepository.findFollowByFollowerAndFollowing(
			user, followUser);
		if (alreadyFollow.isPresent()) {
			followRepository.delete(alreadyFollow.get());
			return new ResponseDto(HttpStatus.OK, "cancel", null);
		}
		Follow follow = Follow.builder()
			.follower(user)
			.following(followUser)
			.build();
		Follow save = followRepository.save(follow);
		NotificationCreateDto notificationCreateDto = NotificationCreateDto.builder()
			.notificationType(NotificationType.FOLLOW)
			.sender_profile_url(user.getProfileImagePath())
			.sender_id(userId)
			.following_id(followUser.getId())
			.build();
		notificationService.createNotification(notificationCreateDto);
		return new ResponseDto(HttpStatus.OK, "success",
			save.getId());
	}
}
