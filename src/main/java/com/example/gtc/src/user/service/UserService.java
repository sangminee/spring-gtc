package com.example.gtc.src.user.service;

import com.example.gtc.config.BaseException;
import com.example.gtc.src.user.entity.User;
import com.example.gtc.src.user.repository.dto.response.*;
import com.example.gtc.src.user.repository.dto.request.PostUserEmailJoinReq;
import com.example.gtc.src.user.repository.dto.request.PostUserPhoneJoinReq;
import com.example.gtc.src.user.repository.dto.request.PostLoginEmailReq;
import com.example.gtc.src.user.repository.dto.request.PostLoginNicknameReq;
import com.example.gtc.src.user.repository.dto.request.PostLoginPhoneReq;

import java.util.List;

public interface UserService {
    GetUserProfileRes retrieveUserProfile(String userId, Long id) throws BaseException;

    PostJoinUserRes createUserPhone(PostUserPhoneJoinReq postUserReq) throws BaseException;
    PostJoinUserRes createUserEmail(PostUserEmailJoinReq postUserReq) throws BaseException;

    void checkUserPhone(String nickname) throws BaseException;
    void checkUserNickname(String nickname) throws BaseException;

    PostLoginRes logInPhone(PostLoginPhoneReq postLoginReq) throws BaseException;
    PostLoginRes logInEmail(PostLoginEmailReq postLoginReq) throws BaseException;
    PostLoginRes logInNickname(PostLoginNicknameReq postLoginReq) throws BaseException;

    String decryptPassword(User user) throws BaseException;

    PostEditUserRes editPassword(String nickname, String password, Long userId) throws BaseException;
    PostEditUserRes editUserImg(Long userId, String userImgUrl) throws BaseException;
    PostEditUserRes editWebsite(Long userId, String website) throws BaseException;
    PostEditUserRes editBio(Long userId, String bio) throws BaseException;
    PostEditUserRes editName(String nickname, String name) throws BaseException;
    PostEditUserRes editNickname(Long userId, String changeNickname) throws BaseException;
    PostEditUserRes editState(String nickname, int state) throws BaseException;

    DeleteUserRes deleteUser(String nickname, String password, Long userId) throws BaseException;

    // Follower
    List<PostFollowerRes> createFollower(Long userId, String followingNickname) throws BaseException;
}
