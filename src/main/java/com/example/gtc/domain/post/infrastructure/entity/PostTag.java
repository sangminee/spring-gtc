package com.example.gtc.domain.post.infrastructure.entity;

import com.example.gtc.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long tagId;  // 시스템이 저장하는 id

    @ManyToOne
    @JoinColumn(name="postId")  // post를 작성한 사람이 tag의 유저
    private Post post;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    public static PostTag toEntity(Post post, User user) {
        PostTag postTag = new PostTag();
        postTag.setPost(post);
        postTag.setUser(user);
        return postTag;
    }
}
