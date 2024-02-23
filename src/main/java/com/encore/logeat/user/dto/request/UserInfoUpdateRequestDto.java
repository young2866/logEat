package com.encore.logeat.user.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserInfoUpdateRequestDto {
    private String nickname;
    private String introduce;

}
