package com.example.gtc.domain.user.repository.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "로그인")
public class PostLoginNicknameReq {

    @Schema(description = "사용자 이름을 입력해 주세요.")
    private String nickname;
    @Schema(description = "비밀번호를 입력해 주세요.")
    private String password;
}
