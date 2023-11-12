package com.example.gtc.domain.post.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Queue;

@Getter
@Setter
@Schema(description = "게시글 작성")
public class PostWriteReq {

    @Schema(description  = "게시글 내용을 입력해주세요")
    private String postContent;

    @Schema(description  = "등록할 사진의 갯수를 입력하세요.")
    private int photoCount;

    @Schema(description  = "사진 url을 입력하세요.")
    private Queue<String> queue;

}
