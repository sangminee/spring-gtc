package com.example.gtc.domain.user.repository.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "팔로우정보")
public class PostFollowerRes {
    private String message;
    private String myNickname;
    private String followingNickname;
    private int agree;
    private int state;
}
