package com.example.gtc.src.user.repository.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "로그인")
public class PostLoginEmailReq {

    @ApiModelProperty(notes = "이메일을 입력해 주세요.")
    private String email;
    @ApiModelProperty(notes = "비밀번호를 입력해 주세요.")
    private String password;
}
