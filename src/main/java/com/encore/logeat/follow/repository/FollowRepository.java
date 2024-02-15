package com.encore.logeat.follow.repository;


import com.encore.logeat.follow.domain.Follow;
import com.encore.logeat.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	Optional<Follow> findFollowByFollowerAndFollowing(User follower, User following);
}
