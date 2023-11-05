package com.example.gtc.domain.comment.entity;

import com.example.gtc.common.global.ReportList;
import com.example.gtc.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CommentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long commentReportId;  // 시스템이 저장하는 id

    @ManyToOne
    @JoinColumn(name="commentId")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @OneToOne
    @JoinColumn(name="reportListId")
    private ReportList reportList;

    private LocalDateTime commentReportTime;
    private int state;

    public static CommentReport toEntity(Comment comment, User user, ReportList reportList) {
        CommentReport commentReport = new CommentReport();
        commentReport.setComment(comment);
        commentReport.setUser(user);
        commentReport.setReportList(reportList);
        commentReport.setCommentReportTime(LocalDateTime.now());
        return commentReport;
    }
}
