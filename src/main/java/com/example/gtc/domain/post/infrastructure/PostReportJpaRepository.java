package com.example.gtc.domain.post.infrastructure;

import com.example.gtc.domain.post.infrastructure.entity.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportJpaRepository extends JpaRepository<PostReport,Long> {
}
