package com.encore.logeat.post.Service;

import com.encore.logeat.follow.domain.Follow;
import com.encore.logeat.post.Dto.RequestDto.PostCreateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostSecretUpdateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostUpdateRequestDto;
import com.encore.logeat.post.Dto.ResponseDto.PostDetailResponseDto;
import com.encore.logeat.post.Dto.ResponseDto.PostSearchResponseDto;
import com.encore.logeat.post.domain.Post;
import com.encore.logeat.post.repository.PostRepository;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.repository.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String[] split = name.split(":");
        long userId = Long.parseLong(split[0]);

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("not found user"));

        Post newPost = Post.builder()
                .title(postCreateRequestDto.getTitle())
                .contents(postCreateRequestDto.getContents())
                .category(postCreateRequestDto.getCategory())
                .location(postCreateRequestDto.getLocation())
                .user(user)
                .build();
        return postRepository.save(newPost);
    }

    @PreAuthorize("hasAuthority('USER')")
    public Post update(Long id, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found post"));
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String[] split = name.split(":");
        long userId = Long.parseLong(split[0]);

        if (userId == post.getUser().getId()) {
            post.updatePost(
                    postUpdateRequestDto.getTitle(),
                    postUpdateRequestDto.getContents(),
                    postUpdateRequestDto.getCategory(),
                    postUpdateRequestDto.getLocation()
            );
        } else {
            throw new RuntimeException("본인이 작성한 글만 수정 가능합니다.");
        }
        return post;
    }

    public Page<PostSearchResponseDto> findAllAccessiblePosts(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;
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
        Long userId = null;
        if (authentication != null && authentication.isAuthenticated() && !"anonymous".equals(authentication.getName())) {
            try {
                String name = authentication.getName();
                String[] split = name.split(":");
                userId = Long.parseLong(split[0]);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing user ID from authentication for title search.");
            }
        }
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

        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("글을 찾을수 없습니다."));
        if (userId == post.getUser().getId()) {
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
        } else {
            throw new EntityNotFoundException("아이디가 일치하지 않습니다.");
        }
        return post;
    }

    public PostDetailResponseDto postDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("not found post"));
        PostDetailResponseDto postDetailResponseDto = PostDetailResponseDto.toPostDetailResponseDto(post);
        return postDetailResponseDto;
    }

    @PreAuthorize("hasAuthority('USER')")
    public Page<PostSearchResponseDto> postFollowingLatestPost(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;
        if (authentication != null && authentication.isAuthenticated() && !"anonymous".equals(authentication.getName())) {
            try {
                String[] split = authentication.getName().split(":");
                userId = Long.parseLong(split[0]);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing user ID from authentication.");
            }
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
        // 로그인한 user가 팔로우한 리스트를 가져옴
        List<Follow> followingList = user.getFollowingList();
        // 찾은 포스트를 담기 위한 리스트 생성
        List<PostSearchResponseDto> findFollowUserPost = new ArrayList<>();

        for (Follow follow : followingList) {
            User following = follow.getFollowing();
            // 팔로잉한 유저의 게시글 들을 가져와서 리스트에 담음
            List<Post> followingPostList = postRepository.findLatestPostByUserFollowing(following.getId());
            if (!followingPostList.isEmpty()) {
                // findLatestPostByUserFollowing에서 최신글(비공개글 제외) 순서로 불러옴
                // 따라서 리스트의 0번째를 꺼내면 팔로잉한 유저의 최신글을 가져옴
                Post latestPost = followingPostList.get(0);
                PostSearchResponseDto postSearchResponseDto = PostSearchResponseDto.toPostSearchResponseDto(latestPost);
                findFollowUserPost.add(postSearchResponseDto);
            }
        }

        return new PageImpl<>(findFollowUserPost, pageable, findFollowUserPost.size());
    }

}
