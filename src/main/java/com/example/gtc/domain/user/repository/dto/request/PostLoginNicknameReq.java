package com.example.gtc.domain.user.repository.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "로그인")
public class PostLoginNicknameReq {

    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요.")
    private String nickname;
    @ApiModelProperty(notes = "비밀번호를 입력해 주세요.")
    private String password;
}
