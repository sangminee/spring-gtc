package com.example.gtc.src.user.repository.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private int userUpdateNameCount;
    private LocalDateTime userUpdateNameTime;

    private String agree;
    private int state;
}
