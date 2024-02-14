package com.encore.logeat.follow.service;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.follow.domain.Follow;
import com.encore.logeat.follow.repository.FollowRepository;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.repository.UserRepository;
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
	@Autowired
	public FollowService(FollowRepository followRepository, UserRepository userRepository) {
		this.followRepository = followRepository;
		this.userRepository = userRepository;
	}


	@PreAuthorize("hasAuthority('USER')")
	public ResponseDto addFollow(Long id) {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		String[] split = name.split(":");
		long userId = Long.parseLong(split[0]);

		if (userId == id) {
			throw new IllegalArgumentException("자신을 팔로우 할 수 없습니다.");
		}
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("예기치 못한 에러가 발생하였습니다."));
		User followUser = userRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("팔로우를 할 유저가 존재하지 않습니다."));
		Follow follow = Follow.builder()
			.follower(user)
			.following(followUser)
			.build();
		Follow save = followRepository.save(follow);
		return new ResponseDto(HttpStatus.OK, followUser.getNickname() + "님을 팔로우 하였습니다.",
			save.getId());
	}
}