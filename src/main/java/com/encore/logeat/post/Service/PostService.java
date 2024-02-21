package com.encore.logeat.post.Service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.encore.logeat.common.s3.S3Config;
import com.encore.logeat.notification.domain.NotificationType;
import com.encore.logeat.notification.dto.request.NotificationCreateDto;
import com.encore.logeat.notification.service.NotificationService;
import com.encore.logeat.post.Dto.RequestDto.PostCreateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostSecretUpdateRequestDto;
import com.encore.logeat.post.Dto.RequestDto.PostUpdateRequestDto;
import com.encore.logeat.post.Dto.ResponseDto.PostDetailResponseDto;
import com.encore.logeat.post.Dto.ResponseDto.PostSearchResponseDto;
import com.encore.logeat.post.domain.Post;
import com.encore.logeat.post.repository.PostRepository;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.repository.UserRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final S3Config s3Config;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository,
        NotificationService notificationService, S3Config s3Config) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.s3Config = s3Config;
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

    public PostDetailResponseDto postDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("not found post"));
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
}
