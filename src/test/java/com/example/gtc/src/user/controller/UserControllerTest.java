package com.example.gtc.src.user.controller;

import com.example.gtc.config.BaseException;
import com.example.gtc.src.user.repository.FollowingJpaRepository;
import com.example.gtc.src.user.repository.UserJpaRepository;
import com.example.gtc.src.user.repository.dto.request.PostUserPhoneJoinReq;
import com.example.gtc.src.user.repository.dto.response.PostJoinUserRes;
import com.example.gtc.src.user.service.UserServiceImpl;
import com.example.gtc.utils.JwtService;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    FollowingJpaRepository followingJpaRepository;

    @BeforeEach // test 실행전에 무조건 실행됨
    public void beferEach(){
        jwtService = new JwtService();
        userService = new UserServiceImpl(userJpaRepository, followingJpaRepository, jwtService);
    }

    @After("")
    public void tearDown() throws Exception {
        userJpaRepository.deleteAll();
    }

    @Test
    void 핸드폰으로_회원가입() throws BaseException {
//        // given : 테스트에 필요한 데이터들을 셋팅하는 단계
//        PostUserPhoneJoinReq postUserPhoneReq = new PostUserPhoneJoinReq();
//        postUserPhoneReq.setName("test02");
//        postUserPhoneReq.setNickname("test02");
//        postUserPhoneReq.setPassword("test02!");
//        postUserPhoneReq.setPhone("12345078");
//        postUserPhoneReq.setBirth(19991026);
//        postUserPhoneReq.setAgree("Y");
//
//        // when : 직접 실행하는 단계
//        PostJoinUserRes postJoinUserRes = userService.createUserPhone(postUserPhoneReq);
//        PostJoinUserRes postJoinUserResTest = new PostJoinUserRes("회원가입이 완료되었습니다.","test02");
//
//        // then : 단언문(assert, assertThat 등)을 통해 success, fail 결과를 유도, 판단하는 단계
//        Assertions.assertThat(postJoinUserRes.getNickname()).isEqualTo(postJoinUserResTest.getNickname());
//
    }

    @Test
    void logIn() {
    }

    @Test
    void retrieveUserProfile() {
    }

    @Test
    void editPassword() {
    }

    @Test
    void editNickname() {
    }

    @Test
    void deleteUser() {
    }
}