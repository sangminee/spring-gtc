package com.example.gtc.src.user.service;

import com.example.gtc.config.BaseException;
import com.example.gtc.src.user.entity.User;
import com.example.gtc.src.user.repository.UserJpaRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.gtc.config.BaseResponseStatus.*;

@Service
public class KakaoServiceImpl {

    private final UserJpaRepository userJpaRepository;

    public KakaoServiceImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }


    public String getKakaoAccessToken (String code) throws BaseException {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //HttpURLConnection 설정 값 셋팅
            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=504a8b6ebb986e5683e9a7841356e500"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:8080/oauth/kakao"); // TODO 인가코드 받은 redirect_uri 입력

            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

//            //결과 코드가 200이라면 성공
//            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode : " + responseCode);

            //  RETURN 값 result 변수에 저장
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
//            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            // 토큰 값 저장 및 리턴
            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    public Map<String,Object> getUserInfo(String access_token) {
        Map<String,Object> resultMap =new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String br_line = "";
            String result = "";

            while ((br_line = br.readLine()) != null) {
                result += br_line;
            }
            System.out.println("response:" + result);


            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
//            log.warn("element:: " + element);
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

//            log.warn("id:: "+element.getAsJsonObject().get("id").getAsString());
            String id = element.getAsJsonObject().get("id").getAsString();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();

            String email = kakao_account.getAsJsonObject().get("email").getAsString();
//            log.warn("email:: " + email);

            resultMap.put("nickname", nickname);
            resultMap.put("id", id);
            resultMap.put("email", email);

            System.out.println("걀과 : "+resultMap);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultMap;
    }


    public void createUser(String snsId, String userName, String email, String userpw) throws BaseException {
        Optional<User> findUserEmail = userJpaRepository.findByEmail(email);
        Optional<User> findNickname = userJpaRepository.findByNickname(snsId);

        if(!findUserEmail.isEmpty()){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        if(!findNickname.isEmpty()){
            throw new BaseException(DUPLICATED_NICKNAME);
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(userpw);
        user.setNickname(snsId);
        user.setName(userName);
        user.setUserCreateTime(LocalDateTime.now());
        user.setJoinType(1);
        user.setAgree("Y");
        userJpaRepository.save(user);
    }
}
