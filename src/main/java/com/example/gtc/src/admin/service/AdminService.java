package com.example.gtc.src.admin.service;

import com.example.gtc.config.BaseException;
import com.example.gtc.src.post.repository.dto.response.GetPost;
import com.example.gtc.src.user.entity.User;

import java.util.List;

public interface AdminService {

    List<User> getUsersbyAdmin(Long userId) throws BaseException;
    List<GetPost> getPostsbyAdmin(Long userId) throws BaseException;
}
