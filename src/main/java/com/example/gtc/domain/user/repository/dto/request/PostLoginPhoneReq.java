package com.example.gtc.domain.user.repository.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "로그인")
public class PostLoginPhoneReq {

    @Schema(description = "핸드폰 번호를 입력해 주세요.")
    private String phone;
    @Schema(description = "비밀번호를 입력해 주세요.")
    private String password;

}
