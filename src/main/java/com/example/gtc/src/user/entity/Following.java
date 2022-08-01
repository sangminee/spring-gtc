package com.example.gtc.src.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Following {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long followingId;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    private String followingNickname;
    private int agree;
    private int state;
}
