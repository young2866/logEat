package com.encore.logeat.post.Dto.ResponseDto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class PostLikeWeekResponseDto {
    private Long postId;
    private String title;
    private String category; // List로 변경하기
    private MultipartFile postImage;
}
