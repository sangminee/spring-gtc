package com.example.gtc.src.post.repository.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "게시글 정보")
public class GetPost {
    private String postContent;
}
