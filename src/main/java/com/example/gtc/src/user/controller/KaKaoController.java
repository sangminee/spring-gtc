package com.example.gtc.src.user.controller;

import com.example.gtc.src.user.service.KakaoServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags ="카카오 로그인 API")
@RequestMapping("/kakao")
public class KaKaoController {

    @Autowired
    KakaoServiceImpl kakaoService;


}
