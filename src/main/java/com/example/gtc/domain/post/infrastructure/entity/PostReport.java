package com.example.gtc.domain.post.infrastructure.entity;

import com.example.gtc.common.global.ReportList;
import com.example.gtc.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PostReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long postReportId;  // 시스템이 저장하는 id

    @ManyToOne
    @JoinColumn(name="postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @OneToOne
    @JoinColumn(name="reportListId")
    private ReportList reportList;

    private LocalDateTime postReportTime;

    private int state;

    public static PostReport toEntity(Post post, User user, ReportList reportList) {
        PostReport postReport = new PostReport();
        postReport.setPost(post);
        postReport.setUser(user);
        postReport.setReportList(reportList);
        postReport.setPostReportTime(LocalDateTime.now());
        postReport.setState(0);
        return postReport;
    }
}
