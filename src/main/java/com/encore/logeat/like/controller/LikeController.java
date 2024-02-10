package com.encore.logeat.like.controller;

import com.encore.logeat.common.dto.ResponseDto;
import com.encore.logeat.like.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("post/{id}/like")
    public ResponseEntity<ResponseDto> postLike(@PathVariable Long id) {
        ResponseDto responseDto = likeService.postLike(id);
        return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());
    }
}
