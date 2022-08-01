package com.example.gtc.src.user.repository.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "로그인")
public class PostLoginPhoneReq {

    @ApiModelProperty(notes = "핸드폰 번호를 입력해 주세요.")
    private String phone;
    @ApiModelProperty(notes = "비밀번호를 입력해 주세요.")
    private String password;

}
