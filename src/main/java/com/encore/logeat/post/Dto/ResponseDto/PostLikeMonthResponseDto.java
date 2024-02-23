package com.encore.logeat.post.Dto.ResponseDto;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Builder
public class PostLikeMonthResponseDto {
    private Long postId;
    private String title;
    private String category; // List로 변경하기
    private String profileImagePath;
}
