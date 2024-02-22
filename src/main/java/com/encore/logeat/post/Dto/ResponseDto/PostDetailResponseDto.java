package com.encore.logeat.post.dto.ResponseDto;

import com.encore.logeat.post.domain.Post;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponseDto {

	private Long id;
	private String title;
	private String contents;
	private String userNickname;
	private String secretYn;
	private int likeCount;
	private String category;
	private String location;
	private String createdTime;

	public static PostDetailResponseDto toPostDetailResponseDto(Post post) {
		PostDetailResponseDto.PostDetailResponseDtoBuilder builder = PostDetailResponseDto.builder();
		builder.id(post.getId());
		builder.title(post.getTitle());
		builder.contents(post.getContents());
		builder.userNickname(post.getUser().getNickname());
		builder.likeCount(post.getLikeCount());
		builder.category(post.getCategory());
		builder.location(post.getLocation());
		builder.secretYn(post.getSecretYorN());
		builder.createdTime(
			post.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

		return builder.build();
	}
}
