package com.example.gtc.domain.admin.service;

import com.example.gtc.common.config.BaseException;
import com.example.gtc.domain.post.repository.dto.response.GetPost;
import com.example.gtc.domain.user.entity.User;

import java.awt.print.Pageable;
import java.util.List;

public interface AdminService {

    List<User> getUsersbyAdmin(Long userId) throws BaseException;
    List<GetPost> getPostsbyAdmin(Long userId, Pageable pageable) throws BaseException;
}
