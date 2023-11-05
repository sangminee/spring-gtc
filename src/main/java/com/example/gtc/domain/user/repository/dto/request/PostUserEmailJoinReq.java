package com.example.gtc.domain.user.repository.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel(description = "회원가입")
public class PostUserEmailJoinReq {

    @ApiModelProperty(notes = "이름을 입력해 주세요.")
    private String name;
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요.")
    private String nickname;
    @ApiModelProperty(notes = "이메일을 입력해 주세요.")
    private String email;
    @ApiModelProperty(notes = "비밀번호를 입력해 주세요.")
    private String password;

    @ApiModelProperty(notes = "생일을 입력해 주세요.")
    private int birth;

    @ApiModelProperty(notes = "회원가입 시각을 입력해 주세요.")
    private LocalDateTime userCreateTime;

    @ApiModelProperty(notes = "동의유무를 입력해 주세요.")
    private String agree;
}
