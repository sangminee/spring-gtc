package com.example.gtc.domain.user.repository.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로그인")
public class PostLoginRes {
    private String message;
    private String nickname;
    private String jwt;

    private String agree;
    private int state;
}
