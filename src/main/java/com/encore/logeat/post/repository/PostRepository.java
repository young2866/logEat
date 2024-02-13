package com.encore.logeat.post.repository;

import com.encore.logeat.post.domain.Post;
import com.encore.logeat.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findPostByTitleContaining(String titleKeyword, Pageable pageable);
    Page<Post> findPostByCategory(String category, Pageable pageable);
    Page<Post> findByUserNickname(String user_nickname, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
}
