package com.example.gtc.domain.post.repository.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Queue;

@Getter
@Setter
@ApiModel(description = "게시글 태그")
public class PostTagReq {

    @ApiModelProperty(notes = "사용자 이름을 입력해주세요.")
    private Queue<String> nickname;
}
