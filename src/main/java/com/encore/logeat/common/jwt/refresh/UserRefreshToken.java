package com.encore.logeat.common.jwt.refresh;

import com.encore.logeat.user.domain.User;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserRefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;
	private String refreshToken;
	private Long reissueCount = 0L;

	public UserRefreshToken(User user, String refreshToken) {
		this.user = user;
		this.refreshToken = refreshToken;
	}

	public void updateUserRefreshToken(String refreshToken) {
		this.reissueCount = 0L;
		this.refreshToken = refreshToken;
	}

	public boolean validateRefreshToken(String refreshToken) {
		return this.refreshToken.equals(refreshToken);
	}

	public void increaseReissueCount() {
		this.reissueCount++;
	}
}