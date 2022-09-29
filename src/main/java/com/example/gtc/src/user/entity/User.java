package com.example.gtc.src.user.entity;

import com.example.gtc.src.user.repository.dto.request.PostUserPhoneJoinReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    private Long userId;  // 시스템이 저장하는 id

    private String nickname;
    private String email;
    private String phone;
    private String password;
    private String name;
    private int birth;

    @Embedded
    private UserInfo userInfo;

    private LocalDateTime userCreateTime;
    private LocalDateTime loginTime;
    private int userUpdateNameCount;
    private LocalDateTime userUpdateNameTime;
    private String agree;

    private int userType;
    private int joinType;
    private int state;

    public static User toEntity(PostUserPhoneJoinReq postUserReq) {
        User user = new User();
        user.setNickname(postUserReq.getNickname());
        user.setName(postUserReq.getName());
        user.setPhone(postUserReq.getPhone());
        user.setPassword(postUserReq.getPassword());
        user.setBirth(postUserReq.getBirth());
        user.setUserCreateTime(LocalDateTime.now());
        user.setAgree(postUserReq.getAgree());
        user.setUserType(postUserReq.getUserType());
        UserInfo userInfo = new UserInfo("","","");
        user.setUserInfo(userInfo);
        return user;
    }
}
