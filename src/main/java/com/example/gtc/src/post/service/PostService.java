package com.example.gtc.src.post.service;

import com.example.gtc.config.BaseException;
import com.example.gtc.src.post.entity.Post;
import com.example.gtc.src.post.repository.dto.request.PostWriteReq;
import com.example.gtc.src.post.repository.dto.response.PostWriteRes;

public interface PostService {
    PostWriteRes createPost(Long userId, PostWriteReq postWriteReq)  throws BaseException;
    void createPhoto(String url, Post post)  throws BaseException;
}
