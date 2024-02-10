package com.encore.logeat.post.controller;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.post.Dto.RequestDto.PostCreateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostSecretUpdateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostUpdateRequestDto;
import com.encore.logeat.post.Dto.ResponseDto.PostSearchResponseDto;
import com.encore.logeat.post.Service.PostService;
import com.encore.logeat.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    @PatchMapping("/post/{id}/update")
    public ResponseEntity<ResponseDto> itemUpdate(@PathVariable Long id, PostUpdateRequestDto postUpdateRequestDto){
        Post post = postService.update(id, postUpdateRequestDto);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK, "post successfully updated", post.getId()), HttpStatus.OK);
    }

    @GetMapping("/post/search/title")
    public Page<PostSearchResponseDto> postIncludeTitleSearch(@RequestParam(value = "titleKeyword") String titleKeyword, @PageableDefault(size = 9) Pageable pageable) {
        Page<PostSearchResponseDto> postSearchResponseDtoList = postService.postTitleSearch(titleKeyword, pageable);
        return postSearchResponseDtoList;
    }
    @GetMapping("/post/search/userName")
    public Page<PostSearchResponseDto> postIncludeUserNameSearch(@RequestParam(value = "userName") String userName, @PageableDefault(size = 9) Pageable pageable) {
        Page<PostSearchResponseDto> postSearchResponseDtoList = postService.postIncludeUserNameSearch(userName, pageable);
        return postSearchResponseDtoList;
    }

    @GetMapping("/post/search/category")
    public Page<PostSearchResponseDto> postIncludeCategorySearch(@RequestParam(value = "category") String category, @PageableDefault(size = 9) Pageable pageable) {
        Page<PostSearchResponseDto> postSearchResponseDtoList = postService.postIncludeCategorySearch(category, pageable);
        return postSearchResponseDtoList;
    }

    @GetMapping("/post/main")
    public Page<PostSearchResponseDto> postMainView(@PageableDefault(size = 9, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostSearchResponseDto> postSearchResponseDtos = postService.postView(pageable);
        return postSearchResponseDtos;
    }

    @DeleteMapping("/post/{id}/delete")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return new ResponseEntity<>(
                new ResponseDto(HttpStatus.OK, "Delete Finish!", null),
                HttpStatus.OK);
    }
    @PatchMapping("/post/{id}/secretYn")
    public ResponseEntity<ResponseDto> secretPostStatus(@PathVariable Long id, PostSecretUpdateRequestDto postSecretUpdateRequestDto){
        Post post = postService.updateSecretStatus(id,postSecretUpdateRequestDto);
            return new ResponseEntity<>(
                    new ResponseDto(HttpStatus.OK, "비밀글 설정 완료", post.getId()),HttpStatus.OK);

    }


}
