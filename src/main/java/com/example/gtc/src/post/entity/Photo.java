package com.example.gtc.src.post.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long photoId;  // 시스템이 저장하는 id

    @ManyToOne
    @JoinColumn(name="postId")
    private Post post;

    private String photoUrl;
    private int state;

    public static Photo toEntity(String url,Post post){
        Photo photo = new Photo();
        photo.setPhotoUrl(url);
        photo.setPost(post);
        photo.setState(0); // 활성
        return photo;
    }
}
