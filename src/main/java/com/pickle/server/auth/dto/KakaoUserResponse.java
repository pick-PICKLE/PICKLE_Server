package com.pickle.server.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.Properties;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserResponse {
    //카카오로 받아오는 유저 정보

    private Long id;
    private Properties properties;
    private KakaoAccount kakao_account;

    @ToString
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
        private String nickname;
        private String profile_image;
    }
    @Data
    @ToString
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoAccount {
        private String email;
        private String gender;
    }
}
