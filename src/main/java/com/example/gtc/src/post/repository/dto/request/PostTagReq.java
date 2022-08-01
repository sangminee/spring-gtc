package com.example.gtc.src.post.repository.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "게시글 태그")
public class PostTagReq {
    @ApiModelProperty(notes = "userId를 입력해주세요.")
    private long userId;
}
