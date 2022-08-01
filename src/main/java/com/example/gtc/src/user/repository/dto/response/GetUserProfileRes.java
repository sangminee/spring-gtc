package com.example.gtc.src.user.repository.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(description = "프로필정보 보기")
public class GetUserProfileRes {
    private String nickname;
    private String name;

    public GetUserProfileRes() {
    }
}
