package com.encore.logeat.post.Service;

import com.encore.logeat.post.Dto.RequestDto.PostCreateRequestDto;
import com.encore.logeat.post.Dto.ResponseDto.PostSearchResponseDto;
import com.encore.logeat.post.domain.Post;
import com.encore.logeat.post.repository.PostRepository;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    @PreAuthorize("hasAuthority('USER')")
    public Post createPost(PostCreateRequestDto postCreateRequestDto) {
//        MultipartFile multipartFile = postCreateRequestDto.getPostImage();
//        String fileName = multipartFile.getOriginalFilename();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String[] split = name.split(":");
        long userId = Long.parseLong(split[0]);

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());

        Post new_post = Post.builder()
                .title(postCreateRequestDto.getTitle())
                .contents(postCreateRequestDto.getContents())
                .category(postCreateRequestDto.getCategory())
                .location(postCreateRequestDto.getLocation())
                .user(user)
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

    public List<PostSearchResponseDto> postTitleSearch(String titleKeyword) {
        List<Post> post = postRepository.findPostByTitleContaining(titleKeyword);

        return post.stream().map(PostSearchResponseDto::toPostSearchResponseDto).collect(Collectors.toList());
    }

    public List<PostSearchResponseDto> postCategorySearch(String category) {
        List<Post> post = postRepository.findPostByCategory(category);

        return post.stream().map(PostSearchResponseDto::toPostSearchResponseDto).collect(Collectors.toList());
    }


    public List<PostSearchResponseDto> postUserNameSearch(String userNickname) {
        List<Post> post = postRepository.findByUserNickname(userNickname);
        return post.stream().map(PostSearchResponseDto::toPostSearchResponseDto).collect(Collectors.toList());
    }
    public void deletePost(Long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String[] split = name.split(":");
        long userId = Long.parseLong(split[0]);
        System.out.println("userId = " + userId);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("글을 찾을수 없습니다."));
        if(userId == post.getUser().getId()){
            postRepository.delete(post);
        }



    }

}
