package com.example.gtc.src.comment.entity;

import com.example.gtc.src.post.entity.Post;
import com.example.gtc.src.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long commentId;

    @ManyToOne
    @JoinColumn(name="postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    private String commentContent;
    private LocalDateTime commentCreateTime;
    private int state;

    public static Comment toEntity(User user, Post post, String commentContent) {
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setCommentContent(commentContent);
        comment.setCommentCreateTime(LocalDateTime.now());
        comment.setState(0);
        return comment;
    }
}
