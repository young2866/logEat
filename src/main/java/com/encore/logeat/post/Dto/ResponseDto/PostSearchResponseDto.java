package com.encore.logeat.post.Dto.ResponseDto;

import com.encore.logeat.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.tomcat.jni.Address;

import java.time.LocalDate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Data
@Builder
public class PostSearchResponseDto {
    private Long id;
    private String thumbnailPath;
    private String title;
    private String userNickname;
    private LocalDate createdTime;
    private int likeCount;

//    반복된
    public static PostSearchResponseDto toPostSearchResponseDto(Post post) {
        PostSearchResponseDtoBuilder builder = PostSearchResponseDto.builder();
        builder.id(post.getId());
        builder.thumbnailPath(getThumnailScr(post.getContents()));
        builder.title(post.getTitle());
        builder.userNickname(post.getUser().getNickname());
        builder.createdTime(post.getCreatedTime().toLocalDate());
        builder.likeCount(post.getLikeCount());

        return builder.build();
    }
    private static String getThumnailScr(String contents) {
        Document doc = Jsoup.parse(contents);
        String src = "";
        if(doc.selectFirst("img") != null) {
            src = doc.selectFirst("img").attr("src");
        }
        return src;
    }

}
