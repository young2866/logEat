package com.encore.logeat.user.repository;

import com.encore.logeat.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findByNickname(String nickname);
	boolean existsByNickname(String nickname);
	boolean existsByEmail(String email);
}
