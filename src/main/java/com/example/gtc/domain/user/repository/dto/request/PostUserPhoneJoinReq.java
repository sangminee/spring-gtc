package com.example.gtc.domain.user.repository.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "회원가입")
public class PostUserPhoneJoinReq {

    @Schema(description = "이름을 입력해 주세요.",example = "test00")
    private String name;

    @Schema(description = "사용자 이름을 입력해 주세요.",example = "test00")
    private String nickname;

    @Schema(description = "핸드폰 번호를 입력해 주세요.",example = "0103344556")
    private String phone;

    @Schema(description = "비밀번호를 입력해 주세요.",example = "test00@")
    private String password;

    @Schema(description = "생일을 입력해 주세요.",example = "19991026")
    private int birth;

    @Schema(description = "회원가입 시각을 입력해 주세요.")
    private LocalDateTime userCreateTime;

    @Schema(description = "동의유무를 입력해 주세요.",example = "Y")
    private String agree;

    @Schema(description  = "유저 유형을 입력해 주세요.",example = "0")
    private int userType;
}
