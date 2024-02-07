package com.encore.logeat.post.repository;

import com.encore.logeat.post.domain.Post;
import com.encore.logeat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostByTitleContaining(String titleKeyword);
    List<Post> findPostByCategory(String category);
    List<Post> findByUserNickname(String user_nickname);
}
