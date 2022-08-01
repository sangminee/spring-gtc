package com.example.gtc.src.post.repository;

import com.example.gtc.src.post.entity.PostLike;
import com.example.gtc.src.post.entity.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportJpaRepository extends JpaRepository<PostReport,Long> {
}
