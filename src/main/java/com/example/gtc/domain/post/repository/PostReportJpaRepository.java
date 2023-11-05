package com.example.gtc.domain.post.repository;

import com.example.gtc.domain.post.entity.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportJpaRepository extends JpaRepository<PostReport,Long> {
}
