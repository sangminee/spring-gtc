package com.example.gtc.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor  // 모든 생성자 만들어줌
public class BaseException extends Exception {
    private BaseResponseStatus status;
}