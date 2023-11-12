package com.example.gtc.domain.post.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long photoId;  // 시스템이 저장하는 id

    @ManyToOne
    @JoinColumn(name="postId")
    private Post post;

    private String photoUrl;
    private int state;

    public static PhotoEntity toEntity(String url, Post post){
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setPhotoUrl(url);
        photoEntity.setPost(post);
        photoEntity.setState(0); // 활성
        return photoEntity;
    }
}
