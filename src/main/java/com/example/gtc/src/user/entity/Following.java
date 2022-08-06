package com.example.gtc.src.user.entity;

import com.example.gtc.config.BaseException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.example.gtc.config.BaseResponseStatus.*;

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

    // 내 계정
    public static Following toEntity(User user, User follower) throws BaseException {
        Following following = new Following();
        following.setUser(user);
        following.setFollowingNickname(follower.getNickname());

        // 비공개 계정일 경우
        if(follower.getState() == 1){
            following.setAgree(0);
            following.setState(0);
        }else if(follower.getState() == 0){ // 비공계 계정일 경우
            following.setAgree(1);
            following.setState(0);
        }else{  // 정지된 & 비활성 된 계정일 경우
            throw new BaseException(INVALID__USER);
        }

        return following;
    }
}
