package com.encore.logeat.post.Dto.ResponseDto;

import com.encore.logeat.post.domain.Post;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
public class PostDetailResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String userNickname;
    private int likeCount;
    private String category;
    private String location;
    private String postImage;
    private LocalDate createdTime;

    public static PostDetailResponseDto toPostDetailResponseDto(Post post){
        PostDetailResponseDto.PostDetailResponseDtoBuilder builder = PostDetailResponseDto.builder();
            builder.id(post.getId());
            builder.title(post.getTitle());
            builder.contents(post.getContents());
            builder.userNickname(post.getUser().getNickname());
            builder.likeCount(post.getLikeCount());
            builder.category(post.getCategory());
            builder.location(post.getLocation());
//            builder.postImage(post.getImagePath());
            builder.createdTime(post.getCreatedTime().toLocalDate());

        return builder.build();
    }
}
