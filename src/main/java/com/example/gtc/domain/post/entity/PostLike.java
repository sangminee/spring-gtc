package com.example.gtc.domain.post.entity;

import com.example.gtc.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long postLikeId;  // 시스템이 저장하는 id

    @ManyToOne
    @JoinColumn(name="postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    public static PostLike toEntity(Post post, User user) {
        PostLike postLike = new PostLike();
        postLike.setPost(post);
        postLike.setUser(user);
        return postLike;
    }
}
