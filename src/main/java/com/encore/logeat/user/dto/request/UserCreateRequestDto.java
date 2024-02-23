package com.encore.logeat.user.dto.request;

import com.encore.logeat.user.domain.Role;
import com.encore.logeat.user.domain.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserCreateRequestDto {

	private String email;
	private String nickname;
	private String password;

	public User toEntity() {

		return User.builder()
			.email(this.email)
			.nickname(this.nickname)
			.password(this.password)
			.role(Role.USER)
			.build();
	}
}