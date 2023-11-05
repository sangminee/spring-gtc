package com.example.gtc.domain.comment.entity;

import com.example.gtc.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long commentlikeId;  // 시스템이 저장하는 id

    @ManyToOne
    @JoinColumn(name="commentId")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    private LocalDateTime commentCreateTime;

    public static CommentLike toEntity(User user, Comment comment) {
        CommentLike commentLike = new CommentLike();
        commentLike.setComment(comment);
        commentLike.setUser(user);
        commentLike.setCommentCreateTime(LocalDateTime.now());
        return commentLike;
    }
}
