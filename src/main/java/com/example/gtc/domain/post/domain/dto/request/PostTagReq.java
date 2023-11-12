package com.example.gtc.domain.post.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Queue;

@Getter
@Setter
@Schema(description = "게시글 태그")
public class PostTagReq {

    @Schema(description = "사용자 이름을 입력해주세요.")
    private Queue<String> nickname;
}
