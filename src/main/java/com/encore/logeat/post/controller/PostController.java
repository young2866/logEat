package com.encore.logeat.post.controller;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.post.Dto.RequestDto.PostCreateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostSecretUpdateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostUpdateRequestDto;
import com.encore.logeat.post.Dto.ResponseDto.PostLikeReportResponseDto;
import com.encore.logeat.post.domain.PostLikeReport;
import com.encore.logeat.post.dto.ResponseDto.PostDetailResponseDto;
import com.encore.logeat.post.Dto.ResponseDto.PostSearchResponseDto;
import com.encore.logeat.post.Service.PostService;
import com.encore.logeat.post.domain.Post;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post/new")
    public ResponseEntity<ResponseDto> createPost( PostCreateRequestDto postCreateRequestDto) {
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
        return postService.postTitleSearch(titleKeyword, pageable);
    }

    @GetMapping("/post/search/userName")
    public Page<PostSearchResponseDto> postIncludeUserNameSearch(@RequestParam(value = "userName") String userName, @PageableDefault(size = 9) Pageable pageable) {
        return postService.postIncludeUserNameSearch(userName, pageable);
    }

    @GetMapping("/post/search/category")
    public Page<PostSearchResponseDto> postIncludeCategorySearch(@RequestParam(value = "category") String category, @PageableDefault(size = 9) Pageable pageable) {
        return postService.postIncludeCategorySearch(category, pageable);
    }

    @GetMapping("/post/main")
    public Page<PostSearchResponseDto> postMainView(@PageableDefault(size = 6, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.findAllAccessiblePosts(pageable);
    }

    @DeleteMapping("/post/{id}/delete")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return new ResponseEntity<>(
                new ResponseDto(HttpStatus.OK, "Delete Finish!", null),
                HttpStatus.OK);
    }

    @GetMapping("/post/main/like_desc")
    public Page<PostSearchResponseDto> postMainViewLikeDesc(@PageableDefault(size = 9, sort = "likeCount", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.findAllAccessiblePosts(pageable);
    }

    @GetMapping("/post/mypost")
    public Page<PostSearchResponseDto> viewMypost(@PageableDefault(size = 9, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable){
        return postService.findMyPost(pageable);
    }

    @GetMapping("/post/following/latest-post")
    public Page<PostSearchResponseDto> postFollowingLatestPost(@PageableDefault(size = 5, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.postFollowingLatestPost(pageable);
    }

    @GetMapping("/post/{id}/detail")
    public ResponseEntity<PostDetailResponseDto> postIncludeTitleSearch(@PathVariable Long id) {
        postService.addViewCountCache(id);
        PostDetailResponseDto postDetailResponseDto = postService.postDetail(id);
        return new ResponseEntity<>(postDetailResponseDto, HttpStatus.OK);
    }
    
    @CrossOrigin
    @PostMapping("/post/image/upload")
    public ResponseEntity<?> postImageUpload(@RequestParam("upload") MultipartFile request) {
        try {
            String originName = request.getOriginalFilename();

            String newFileName = java.util.UUID.randomUUID().toString() + "@" + originName;

            String imageUrl = postService.saveFile(request, newFileName);

            return ResponseEntity.ok(Map.of(
                "uploaded", true,
                "fileName", newFileName,
                "originName", originName,
                "url", imageUrl));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "uploaded", false,
                "error", Map.of("message", "파일을 업로드 하지 못했습니다")));
        }
    }
    @GetMapping("/post/like/weeks")
    public ResponseEntity<?> postLikeWeeks() {

        return ResponseEntity.status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES))
                .body(postService.postLikeWeekResponse());
    }

    @GetMapping("/post/like/month")
    public ResponseEntity<?> postLikeMonth() {

        return ResponseEntity.status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES))
                .body(postService.postLikeMonthResponse());
    }

    @PostMapping("/post/like")
    public ResponseEntity<?> postLikeAdd(@RequestParam(value = "postId") Long postId,
                                         @RequestParam(value = "email") String email) {
        PostLikeReportResponseDto postLike = postService.createPostLike(postId, email);
        ResponseDto responseDto = ResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .message("좋아요가 되었습니다")
                .result(postLike)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }


    @DeleteMapping("/post/like")
    public ResponseEntity<?> postLikeDelete(@RequestParam(value = "postId") Long postId,
                                            @RequestParam(value = "email") String email) {
        PostLikeReportResponseDto postLike = postService.deletePostLike(postId, email);
        ResponseDto responseDto = ResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .message("좋아요가 취소되었습니다.")
                .result(postLike)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

}
