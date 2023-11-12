package com.example.gtc.domain.post.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "게시글 정보")
public class GetPost {
    private Long postId;
    private Long userId;
    private String writeNickname;

    private String postContent;
    private LocalDateTime postCreateTime;
    private LocalDateTime postUpdateTime;
    private int state;

    private ArrayList<String> photoUrlList;
    private int likeSum;

}
