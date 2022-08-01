package com.example.gtc.src.user.repository.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "로그인")
public class PostEditUserRes {
    private String message;
    private String nickname;

    private String name;
    private String password;
    private String userImgUrl;
    private String website;
    private String bio;

    private int nameUpdateCount;
    private int nicknameUpdateCount;

    private String agree;
    private int state;
}
