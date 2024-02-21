package com.encore.logeat.post.Service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.encore.logeat.common.s3.S3Config;

import static com.encore.logeat.common.redis.CacheNames.POST;
import static com.encore.logeat.common.redis.CacheNames.createPostCacheKey;
import static com.encore.logeat.common.redis.CacheNames.createViewCountCacheKey;

import com.encore.logeat.common.redis.RedisService;
import com.encore.logeat.post.Dto.RequestDto.PostCreateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostSecretUpdateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostUpdateRequestDto;
import com.encore.logeat.post.dto.ResponseDto.PostDetailResponseDto;
import com.encore.logeat.notification.domain.NotificationType;
import com.encore.logeat.notification.dto.request.NotificationCreateDto;
import com.encore.logeat.notification.service.NotificationService;
import com.encore.logeat.follow.domain.Follow;

import com.encore.logeat.post.Dto.RequestDto.PostCreateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostSecretUpdateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostUpdateRequestDto;
import com.encore.logeat.post.Dto.ResponseDto.PostLikeMonthResponseDto;
import com.encore.logeat.post.Dto.ResponseDto.PostLikeWeekResponseDto;

import com.encore.logeat.post.Dto.ResponseDto.PostSearchResponseDto;
import com.encore.logeat.post.domain.Post;
import com.encore.logeat.post.repository.PostLikeReportRepository;
import com.encore.logeat.post.repository.PostRepository;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.repository.UserRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeReportRepository postLikeReportRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final S3Config s3Config;
    private final RedisService redisService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository,
        NotificationService notificationService, S3Config s3Config, RedisService redisService, PostLikeReportRepository postLikeReportRepository) {

        this.postRepository = postRepository;
        this.postLikeReportRepository = postLikeReportRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.s3Config = s3Config;
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
        Post save = postRepository.save(newPost);

        NotificationCreateDto notificationCreateDto = NotificationCreateDto.builder()
            .notificationType(NotificationType.POST)
            .url_path("/post/" + save.getId() + "/detail")
            .sender_id(userId)
            .build();
        notificationService.createNotification(notificationCreateDto);
        return save;
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

    public String saveFile(MultipartFile request, String newFileName) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(request.getSize());
        metadata.setContentType(request.getContentType());

        s3Config.amazonS3Client().putObject(bucket, newFileName, request.getInputStream(), metadata);
        return s3Config.amazonS3Client().getUrl(bucket, newFileName).toString();

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

    public List<PostLikeWeekResponseDto> postLikeWeekResponse() {
        PageRequest pageRequest = PageRequest.of(0, 3);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        LocalDateTime start = end.minusDays(7);

        return postLikeReportRepository.findPostLikeReportBy(start, end, pageRequest)
                .stream()
                .map(result -> PostLikeWeekResponseDto.builder()
                        .postId(result.getPost().getId())
                        .title(result.getPost().getTitle())
                        .category(result.getPost().getCategory())
                        .postImage(null)
                        .build())
                .collect(Collectors.toList());
    }


    public List<PostLikeMonthResponseDto> postLikeMonthResponse() {
        PageRequest pageRequest = PageRequest.of(0, 3);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        LocalDateTime start = end.minusMonths(1);

        return postLikeReportRepository.findPostLikeReportBy(start, end, pageRequest)
                .stream()
                .map(result -> PostLikeMonthResponseDto.builder()
                        .postId(result.getPost().getId())
                        .title(result.getPost().getTitle())
                        .category(result.getPost().getCategory())
                        .postImage(null)
                        .build())
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('USER')")
    public Page<PostSearchResponseDto> postFollowingLatestPost(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String[] split = authentication.getName().split(":");
        long userId = Long.parseLong(split[0]);

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
        // 로그인한 user가 팔로우한 리스트를 가져옴
        List<Follow> followingList = user.getFollowingList();
        // 찾은 포스트를 담기 위한 리스트 생성
        List<PostSearchResponseDto> findFollowUserPost = new ArrayList<>();

        for (Follow follow : followingList) {
            User following = follow.getFollowing();
            // 팔로잉한 유저의 게시글 들을 가져옴
            Page<Post> followingPostList = postRepository.findLatestPostByUserFollowing(following.getId(),pageable);
            if (!followingPostList.isEmpty()) {
                // findLatestPostByUserFollowing에서 최신글(비공개글 제외) 순서로 불러옴
                // 따라서 content의 0번째를 꺼내면 팔로잉한 유저의 최신글을 가져옴
                Post latestPost = followingPostList.getContent().get(0);
                PostSearchResponseDto postSearchResponseDto = PostSearchResponseDto.toPostSearchResponseDto(latestPost);
                findFollowUserPost.add(postSearchResponseDto);
            }
        }
        // PageImpl: Page 인터페이스 구현체
        // PageImpl<>(페이지에 넣을 리스트, 페이징 데이터인 pageble 객체, 전체 항목수);
        return new PageImpl<>(findFollowUserPost, pageable, findFollowUserPost.size());
    }

}
