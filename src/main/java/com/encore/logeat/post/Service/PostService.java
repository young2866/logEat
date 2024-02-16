package com.encore.logeat.post.Service;

import static com.encore.logeat.common.redis.CacheNames.POST;
import static com.encore.logeat.common.redis.CacheNames.createPostCacheKey;
import static com.encore.logeat.common.redis.CacheNames.createViewCountCacheKey;

import com.encore.logeat.common.redis.RedisService;
import com.encore.logeat.post.Dto.RequestDto.PostCreateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostSecretUpdateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostUpdateRequestDto;
import com.encore.logeat.post.dto.ResponseDto.PostDetailResponseDto;
import com.encore.logeat.post.Dto.ResponseDto.PostSearchResponseDto;
import com.encore.logeat.post.domain.Post;
import com.encore.logeat.post.repository.PostRepository;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.repository.UserRepository;
import java.time.Duration;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final RedisService redisService;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository,
        RedisService redisService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.redisService = redisService;
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

    @Cacheable(cacheNames = POST, key = "#id")
    public PostDetailResponseDto postDetail(Long id) {
        Post post = findById(id);
        PostDetailResponseDto postDetailResponseDto = PostDetailResponseDto.toPostDetailResponseDto(post);
        return postDetailResponseDto;
    }

    public void addViewCountCache(Long postId) {
        String viewCntKey = createViewCountCacheKey(postId);
        if (!redisService.getValues(viewCntKey).equals("false")) {
            redisService.increment(viewCntKey);
            return;
        }

        redisService.setValues(viewCntKey, String.valueOf(findById(postId).getViewCount() + 1),
            Duration.ofMinutes(3));
    }

    @Scheduled(cron = "0 0/3 * * * ?")
    public void applyViewCountToRDB() {
        Set<String> viewCntKeys = redisService.keys("postViewCnt*");
        if(Objects.requireNonNull(viewCntKeys).isEmpty()) return;

        for (String viewCntKey : viewCntKeys) {
            Long postId = extractPostIdFromKey(viewCntKey);
            Integer viewCount = Integer.parseInt(redisService.getValues(viewCntKey));

            postRepository.applyViewCntToRDB(postId, viewCount);
            redisService.deleteValues(viewCntKey);
            redisService.deleteValues(createPostCacheKey(postId));
        }
    }
    private Long extractPostIdFromKey(String viewCntKey) {
        return Long.parseLong(viewCntKey.split("::")[1]);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("해당 글을 찾을 수 없습니다."));
    }
}
