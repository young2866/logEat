package com.encore.logeat.post.Dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
public class PostUpdateRequestDto {
    private Long user_id;
    private String title;
    private String contents;
    private String category;
    private String location;
    private MultipartFile postImage;
}
