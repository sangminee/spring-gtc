package com.example.gtc.src.post.service;

import com.example.gtc.config.BaseException;
import com.example.gtc.src.post.entity.Post;
import com.example.gtc.src.post.entity.PostLike;
import com.example.gtc.src.post.repository.dto.request.PostTagReq;
import com.example.gtc.src.post.repository.dto.request.PostWriteReq;
import com.example.gtc.src.post.repository.dto.response.PostRes;

public interface PostService {
    PostRes createPost(Long userId, PostWriteReq postWriteReq)  throws BaseException;
    PostRes createPostTag(Long userId, int postId, PostTagReq postTagReq) throws BaseException;
    PostRes createPostLike(Long userId, int postId) throws BaseException;

    PostRes deletePostLike(Long userId, int postId) throws BaseException;
    PostRes deletePostTag(Long userId, int postId, PostTagReq postTagReq) throws BaseException;

}
