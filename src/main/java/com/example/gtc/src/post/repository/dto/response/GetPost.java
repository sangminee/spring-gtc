package com.example.gtc.src.post.repository.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Queue;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "게시글 정보")
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
