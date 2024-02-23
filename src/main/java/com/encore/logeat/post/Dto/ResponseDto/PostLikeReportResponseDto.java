package com.encore.logeat.post.Dto.ResponseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostLikeReportResponseDto {
    private String title;
    private String email;
}
