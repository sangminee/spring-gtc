package com.example.gtc.src.comment.entity;

import com.example.gtc.src.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
}
