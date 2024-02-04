package com.encore.logeat.post.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String category;
    private String location;
    private String imagePath;
}
