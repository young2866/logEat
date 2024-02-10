package com.encore.logeat.post.repository;

import com.encore.logeat.post.domain.Post;
import com.encore.logeat.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE (p.user.id = :userId OR p.secretYorN = 'N' OR p.secretYorN IS NULL)")
    Page<Post> findAllAccessiblePosts(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE (p.user.id = :userId OR p.secretYorN = 'N' OR p.secretYorN IS NULL) AND p.title LIKE %:titleKeyword%")
    Page<Post> findPostByTitleContaining(@Param("userId") Long userId, @Param("titleKeyword") String titleKeyword, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE (p.user.id = :userId OR p.secretYorN = 'N' OR p.secretYorN IS NULL) AND p.category = :category")
    Page<Post> findPostByCategory(@Param("userId") Long userId, @Param("category") String category, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.user u WHERE (p.user.id = :userId OR p.secretYorN = 'N' OR p.secretYorN IS NULL) AND u.nickname LIKE %:userNickname%")
    Page<Post> findByUserNickname(@Param("userId") Long userId, @Param("userNickname") String userNickname, Pageable pageable);
}
