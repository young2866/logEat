package com.encore.logeat.junstin;

import com.encore.logeat.post.Dto.RequestDto.PostCreateRequestDto;
import com.encore.logeat.post.Service.PostService;
import com.encore.logeat.post.domain.Post;
import com.encore.logeat.post.domain.PostLikeReport;
import com.encore.logeat.post.repository.PostLikeReportRepository;
import com.encore.logeat.post.repository.PostRepository;
import com.encore.logeat.user.domain.Role;
import com.encore.logeat.user.domain.User;
import com.encore.logeat.user.repository.UserRepository;
import com.encore.logeat.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class MonthPostReadTest {


    private PostRepository postRepository;
    private PostLikeReportRepository postLikeReportRepository;
    private UserRepository userRepository;

    @Autowired
    public MonthPostReadTest(PostRepository postRepository, PostLikeReportRepository postLikeReportRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postLikeReportRepository = postLikeReportRepository;
        this.userRepository = userRepository;
    }


    @Test
    @Transactional
    public void 좋아요월간_주간_테스트() {

        //PageRequest pageRequest = PageRequest.of(0, 5);
        PageRequest pageRequest = PageRequest.of(0, 3);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        LocalDateTime start = end.minusDays(7);
        //LocalDateTime start = end.minusMonths(1);

        Page<PostLikeReport> id = postLikeReportRepository.findPostLikeReportBy(start, end, pageRequest);
        List<PostLikeReport> list = id.toList();
        int a =1;

        Assertions.assertThat(3).isEqualTo(list.size());
        //Assertions.assertThat(5).isEqualTo(list.size());
    }


    @Test
    public void 월간테스트() {

//        List<User> tempUser = List.of(
//                User.builder().email("kim@gmail.com").nickname("김민재").password("12345").introduce("자기소개-김민재").role(Role.USER).build(),
//                User.builder().email("son@gmail.com").nickname("손흥민").password("12345").introduce("자기소개-손흥민").role(Role.USER).build(),
//                User.builder().email("park@gmail.com").nickname("박주영").password("12345").introduce("자기소개-박주영").role(Role.USER).build(),
//                User.builder().email("lee@gmail.com").nickname("이청용").password("12345").introduce("자기소개-이청용").role(Role.USER).build()
//        );
//        userRepository.saveAll(tempUser);
//
//
//        User parkUser = User.builder().email("jisung@gmail.com").nickname("박지성").password("12345").introduce("자기소개-박지성").role(Role.USER).build();
//        userRepository.save(parkUser);
//
//        List<User> all = userRepository.findAll();
//
//        Assertions.assertThat(4).isEqualTo(all.size());


//        List<Post> tempPost = List.of(
//                Post.builder().title("포스트1번 제목").contents("포스트1번 내용").category("포스트1번 카테고리").location("포스트1번 location").user(userList.get(0)).build(),
//                Post.builder().title("포스트2번 제목").contents("포스트2번 내용").category("포스트2번 카테고리").location("포스트2번 location").user(userList.get(1)).build(),
//                Post.builder().title("포스트3번 제목").contents("포스트3번 내용").category("포스트3번 카테고리").location("포스트3번 location").user(userList.get(2)).build(),
//                Post.builder().title("포스트4번 제목").contents("포스트4번 내용").category("포스트4번 카테고리").location("포스트4번 location").user(userList.get(3)).build(),
//                Post.builder().title("포스트5번 제목").contents("포스트5번 내용").category("포스트5번 카테고리").location("포스트5번 location").user(userList.get(3)).build(),
//                Post.builder().title("포스트6번 제목").contents("포스트6번 내용").category("포스트6번 카테고리").location("포스트6번 location").user(userList.get(2)).build(),
//                Post.builder().title("포스트7번 제목").contents("포스트7번 내용").category("포스트7번 카테고리").location("포스트7번 location").user(userList.get(1)).build()
//        );
//        List<Post> postList = postRepository.saveAll(tempPost);
//
//        postList.get(0).addLikeCount();
//        postList.get(0).addLikeCount();
//        PostLikeReport postLikeReport1 = new PostLikeReport(postList.get(0));
//        postLikeReportRepository.save(postLikeReport1);
//
//        postList.get(1).addLikeCount();
//        PostLikeReport postLikeReport2 = new PostLikeReport(postList.get(1));
//        postLikeReportRepository.save(postLikeReport2);
//
//        postList.get(2).addLikeCount();
//        postList.get(2).addLikeCount();
//        PostLikeReport postLikeReport3 = new PostLikeReport(postList.get(2));
//        postLikeReportRepository.save(postLikeReport3);
//
//        postList.get(3).addLikeCount();
//        postList.get(3).addLikeCount();
//        postList.get(3).addLikeCount();
//        PostLikeReport postLikeReport4 = new PostLikeReport(postList.get(3));
//        postLikeReportRepository.save(postLikeReport4);
//
//        postList.get(4).addLikeCount();
//        PostLikeReport postLikeReport5 = new PostLikeReport(postList.get(4));
//        postLikeReportRepository.save(postLikeReport5);
//
//        postList.get(5).addLikeCount();
//        postList.get(5).addLikeCount();
//        PostLikeReport postLikeReport6 = new PostLikeReport(postList.get(5));
//        postLikeReportRepository.save(postLikeReport6);

    }


}
