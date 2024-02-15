package com.encore.logeat.post.Service;

import com.encore.logeat.post.Dto.RequestDto.PostCreateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostSecretUpdateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostUpdateRequestDto;
import com.encore.logeat.post.Dto.ResponseDto.PostSearchResponseDto;
import com.encore.logeat.post.domain.Post;
import com.encore.logeat.post.repository.PostRepository;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
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
    @PreAuthorize("hasAuthority('USER')")
    public Post update(Long id, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found post"));

//    MultipartFile multipartFile = postUpdateRequestDto.getPostImage();
//    String fileName = multipartFile.getOriginalFilename();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String[] split = name.split(":");
        long userId = Long.parseLong(split[0]);

        if(userId == post.getUser().getId()){
            post.updatePost(
                    postUpdateRequestDto.getTitle(),
                    postUpdateRequestDto.getContents(),
                    postUpdateRequestDto.getCategory(),
                    postUpdateRequestDto.getLocation()
//                ,postUpdateRequestDto.getPostImage()
            );
        }else {
            // 업데이트 하는 유저 id랑 게시글 작성한 유저 id랑 다르면 어떤 에러 쓸지 몰라서 일단 RuntimeException갑니다.
            throw new  RuntimeException("본인이 작성한 글만 수정 가능합니다.");
        }
//        = Paths.get("/Users/jang-eunji/Desktop/tmp", post.getId()+"_"+fileName);
//        try{
//            byte[] bytes = multipartFile.getBytes();
//            Files.write(path, bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
//        } catch(IOException e){
//            throw new IllegalArgumentException("image not available");
//        }
        return post;
    }
    public Page<PostSearchResponseDto> postView(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null; // 기본적으로 null로 설정하여 비로그인 상태를 처리
        if (authentication != null && authentication.isAuthenticated() && !"anonymous".equals(authentication.getName())) {
            try {
                String[] split = authentication.getName().split(":");
                userId = Long.parseLong(split[0]);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing user ID from authentication.");

            }
        }
        Page<Post> all = postRepository.findAllAccessiblePosts(userId, pageable);
        return all.map(PostSearchResponseDto::toPostSearchResponseDto);
    }

    public Page<PostSearchResponseDto> postTitleSearch(String titleKeyword, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null; // 기본적으로 null을 설정하여 비로그인 상태를 처리

        if (authentication != null && authentication.isAuthenticated() && !"anonymous".equals(authentication.getName())) {
            // 로그인한 사용자의 경우
            try {
                String name = authentication.getName();
                String[] split = name.split(":");
                userId = Long.parseLong(split[0]); // 인증된 사용자 ID 파싱
            } catch (NumberFormatException e) {
                // 사용자 ID 파싱 실패: 로그인했으나 ID 파싱에 실패한 경우
                System.out.println("Error parsing user ID from authentication for title search.");
            }
        }
        // 로그인한 사용자와 로그인하지 않은 사용자 모두를 처리할 수 있는 쿼리 사용
        Page<Post> posts = postRepository.findPostByTitleContaining(userId, titleKeyword, pageable);

        return posts.map(PostSearchResponseDto::toPostSearchResponseDto);
    }



    public Page<PostSearchResponseDto> postIncludeCategorySearch(String category, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;
        if (authentication != null && authentication.isAuthenticated() && !"anonymous".equals(authentication.getName())) {
            try {
                String[] split = authentication.getName().split(":");
                userId = Long.parseLong(split[0]);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing user ID from authentication for category search.");

            }
        }
        Page<Post> post = postRepository.findPostByCategory(userId, category, pageable);
        return post.map(PostSearchResponseDto::toPostSearchResponseDto);
    }


    public Page<PostSearchResponseDto> postIncludeUserNameSearch(String userName, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;
        if (authentication != null && authentication.isAuthenticated() && !"anonymous".equals(authentication.getName())) {
            try {
                String[] split = authentication.getName().split(":");
                userId = Long.parseLong(split[0]);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing user ID from authentication for username search.");

            }
        }
        Page<Post> post = postRepository.findByUserNickname(userId, userName, pageable);
        return post.map(PostSearchResponseDto::toPostSearchResponseDto);
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


    public Post updateSecretStatus(Long id, PostSecretUpdateRequestDto secretStatus) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("글을 찾지 못했습니다."));
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String[] split = name.split(":");
        long userId = Long.parseLong(split[0]);
        if (userId == post.getUser().getId()) {
            post.setSecret(secretStatus.getSecretYorN());
        }else {
            throw new EntityNotFoundException("아이디가 일치하지 않습니다");
        }

        postRepository.save(post); //  더티체킹 때문에 안해도 되지만 혹시해서 함
        return post;
    }
}
