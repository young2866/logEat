package com.encore.logeat.common.jwt.refresh;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {

	Optional<UserRefreshToken> findByUserIdAndReissueCountLessThan(Long id, int count);

}
