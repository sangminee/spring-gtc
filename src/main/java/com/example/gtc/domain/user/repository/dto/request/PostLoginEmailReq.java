package com.example.gtc.domain.user.repository.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "로그인")
public class PostLoginEmailReq {

    @Schema(description = "이메일을 입력해 주세요.")
    private String email;
    @Schema(description = "비밀번호를 입력해 주세요.")
    private String password;
}
