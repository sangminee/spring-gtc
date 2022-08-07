package com.example.gtc.src.user.controller;

import com.example.gtc.config.BaseException;
import com.example.gtc.src.user.service.KakaoServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@Api(tags ="카카오 로그인 API")
public class KaKaoController {

    private final KakaoServiceImpl kakaoService;

    public KaKaoController(KakaoServiceImpl kakaoService) {
        this.kakaoService = kakaoService;
    }

    @ResponseBody
    @GetMapping("/oauth/kakao")
    public void kakaoCallback(@RequestParam String code) throws IOException, BaseException {
        System.out.println(code);

        // 접속토큰 get
        String kakaoToken = kakaoService.getKakaoAccessToken(code);

        // 접속자 정보 get
        Map<String, Object> result = kakaoService.getUserInfo(kakaoToken);
        String snsId = (String) result.get("id");
        String userName = (String) result.get("nickname");
        String email = (String) result.get("email");
        String userpw = snsId;

        // DB 저장
        kakaoService.createUser(snsId,userName,email,userpw);

    }

}
