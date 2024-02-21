package com.encore.logeat.post.repository;


import com.encore.logeat.post.domain.Post;
import com.encore.logeat.post.domain.PostLikeReport;
import com.encore.logeat.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeReportRepository extends JpaRepository<PostLikeReport, Long> {

//    @Query("SELECT p.post, sum(p.likeCreatedTime) as count " +
//            "FROM PostLikeReport p " +
//            "WHERE p.likeCreatedTime between ?1 AND ?2 " +
//            "GROUP BY p.post ")
//    Page<PostLikeReport> findByPostLikeReport(LocalDateTime start, LocalDateTime endTime, Pageable pageable);

    @Query("SELECT p " +
            "FROM PostLikeReport p " +
            "WHERE p.likeCreatedTime BETWEEN :startDate AND :endDate " +
            "GROUP BY p.post " +
            "ORDER BY COUNT(p.likeCreatedTime) DESC ")
    Page<PostLikeReport> findPostLikeReportBy(@Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate, Pageable pageable);

    Optional<PostLikeReport> findPostLikeReportByPostIdAndUserId(Long postId, Long userId);
}
