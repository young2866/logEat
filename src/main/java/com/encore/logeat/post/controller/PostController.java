package com.encore.logeat.post.controller;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.post.Dto.PostCreateRequestDto;
import com.encore.logeat.post.Service.PostService;
import com.encore.logeat.post.domain.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.security.access.prepost.PreAuthorize;


@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping("/post/new")
    public ResponseEntity<ResponseDto> createPost(PostCreateRequestDto postCreateRequestDto) {
        Post post = postService.createPost(postCreateRequestDto);
        return new ResponseEntity<>(
                new ResponseDto(HttpStatus.CREATED, "new Post Created!", post.getId()),
                HttpStatus.CREATED);
    }

}
