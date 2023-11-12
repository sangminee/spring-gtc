package com.example.gtc.domain.user.repository.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "프로필 정보 보기")
public class GetUserProfileRes {
    private String nickname;
    private String name;
}
