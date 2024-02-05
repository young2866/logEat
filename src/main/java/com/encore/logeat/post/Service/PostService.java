package com.encore.logeat.post.Service;

import com.encore.logeat.post.Dto.PostCreateRequestDto;
import com.encore.logeat.post.domain.Post;
import com.encore.logeat.post.repository.PostRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @PreAuthorize("hasAuthority('USER')")
    public Post createPost(PostCreateRequestDto postCreateRequestDto) {
//        MultipartFile multipartFile = postCreateRequestDto.getPostImage();
//        String fileName = multipartFile.getOriginalFilename();
        Post new_post = Post.builder()
                .title(postCreateRequestDto.getTitle())
                .contents(postCreateRequestDto.getContents())
                .category(postCreateRequestDto.getCategory())
                .location(postCreateRequestDto.getLocation())
                .build();
        Post post = postRepository.save(new_post);

//        Path path = Paths.get("/Users/jang-eunji/Desktop/tmp", post.getId() + "_" + fileName);
//        post.setImagePath(path.toString());
//        try {
//            byte[] bytes = multipartFile.getBytes();
//            Files.write(path, bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
//        } catch (IOException e) {
//            throw new IllegalArgumentException("image not available");
//        }
        return post;
    }



}
