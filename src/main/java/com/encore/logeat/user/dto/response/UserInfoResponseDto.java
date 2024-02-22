package com.encore.logeat.user.dto.response;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserInfoResponseDto {
    private String nickname;
    private String imageUrl;
    private String introduce;
}
