package com.encore.logeat.user.dto.response;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserInfoResponseDto {
    private String nickname;
    private MultipartFile profileImage;
    private String introduce;
}
