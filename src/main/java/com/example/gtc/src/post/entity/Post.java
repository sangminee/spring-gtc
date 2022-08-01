package com.example.gtc.src.post.entity;

import com.example.gtc.src.post.repository.dto.request.PostWriteReq;
import com.example.gtc.src.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long postId;  // 시스템이 저장하는 id

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    private String postContent;
    private LocalDateTime postCreateTime;
    private LocalDateTime postUpdateTime;
    private int state;

    public static Post toEntity(PostWriteReq postWriteReq,User user){
        Post post = new Post();
        post.setPostContent(postWriteReq.getPostContent());
        post.setPostCreateTime(LocalDateTime.now());
        post.setUser(user);
        post.setState(0); // 활성
        return post;
    }
}
