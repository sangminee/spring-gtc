package com.example.gtc.domain.post.repository.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Queue;

@Getter
@Setter
@ApiModel(description = "게시글 작성")
public class PostWriteReq {

    @ApiModelProperty(notes = "게시글 내용을 입력해주세요")
    private String postContent;

    @ApiModelProperty(notes = "등록할 사진의 갯수를 입력하세요.")
    private int photoCount;

    @ApiModelProperty(notes = "사진 url을 입력하세요.")
    private Queue<String> queue;

}
