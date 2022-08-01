package com.example.gtc.src.user.service;

import com.example.gtc.config.BaseException;
import com.example.gtc.src.user.repository.dto.response.DeleteUserRes;
import com.example.gtc.src.user.repository.dto.response.GetUserProfileRes;
import com.example.gtc.src.user.repository.dto.response.PostEditUserRes;
import com.example.gtc.src.user.repository.dto.request.PostUserEmailJoinReq;
import com.example.gtc.src.user.repository.dto.response.PostJoinUserRes;
import com.example.gtc.src.user.repository.dto.request.PostUserPhoneJoinReq;
import com.example.gtc.src.user.repository.dto.request.PostLoginEmailReq;
import com.example.gtc.src.user.repository.dto.request.PostLoginNicknameReq;
import com.example.gtc.src.user.repository.dto.request.PostLoginPhoneReq;
import com.example.gtc.src.user.repository.dto.response.PostLoginRes;

public interface UserService {
    GetUserProfileRes retrieveUserProfile(String userId, Long id) throws BaseException;

    PostJoinUserRes createUserPhone(PostUserPhoneJoinReq postUserReq) throws BaseException;
    PostJoinUserRes createUserEmail(PostUserEmailJoinReq postUserReq) throws BaseException;

    void checkUserPhone(String nickname) throws BaseException;
    void checkUserNickname(String nickname) throws BaseException;

    PostLoginRes logInPhone(PostLoginPhoneReq postLoginReq) throws BaseException;
    PostLoginRes logInEmail(PostLoginEmailReq postLoginReq) throws BaseException;
    PostLoginRes logInNickname(PostLoginNicknameReq postLoginReq) throws BaseException;

    PostEditUserRes editPassword(String nickname, String password, Long userId) throws BaseException;
    PostEditUserRes editUserImg(String nickname, String userImgUrl) throws BaseException;
    PostEditUserRes editWebsite(String nickname, String website) throws BaseException;
    PostEditUserRes editBio(String nickname, String bio) throws BaseException;
    PostEditUserRes editName(String nickname, String name) throws BaseException;
    PostEditUserRes editNickname(String nickname, String changeNickname, Long userId) throws BaseException;
    PostEditUserRes editState(String nickname, int state) throws BaseException;

    DeleteUserRes deleteUser(String nickname, String password, Long userId) throws BaseException;
}
