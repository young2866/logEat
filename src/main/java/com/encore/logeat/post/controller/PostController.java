package com.encore.logeat.post.controller;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.post.Dto.RequestDto.PostCreateRequestDto;
import com.encore.logeat.post.Dto.ResponseDto.PostSearchResponseDto;
import com.encore.logeat.post.Service.PostService;
import com.encore.logeat.post.domain.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/post/search/title")
    public List<PostSearchResponseDto> postIncludeTitleSearch(@RequestParam(value = "titleKeyword") String titleKeyword) {
        List<PostSearchResponseDto> postSearchResponseDtoList = postService.postTitleSearch(titleKeyword);
        return postSearchResponseDtoList;
    }

    @GetMapping("/post/search/userName")
    public List<PostSearchResponseDto> postIncludeUserNameSearch(@RequestParam(value = "userName") String userName) {
        List<PostSearchResponseDto> postSearchResponseDtoList = postService.postUserNameSearch(userName);
        return postSearchResponseDtoList;
    }

    @GetMapping("/post/search/category")
    public List<PostSearchResponseDto> postIncludeCategorySearch(@RequestParam(value = "category") String category) {
        List<PostSearchResponseDto> postSearchResponseDtoList = postService.postCategorySearch(category);
        return postSearchResponseDtoList;
    }
    @DeleteMapping("/post/{id}/delete")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return new ResponseEntity<>(
                new ResponseDto(HttpStatus.OK, "Delete Finish!", null),
                HttpStatus.OK);
    }

}
