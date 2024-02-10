package com.encore.logeat.like.Repository;

import com.encore.logeat.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findLikesByUserIdAndPostId(Long userId, Long postId);

}
