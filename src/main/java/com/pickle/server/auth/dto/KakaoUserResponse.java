package com.pickle.server.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Properties;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserResponse {
    //카카오로 받아오는 유저 정보

    private Long id;
    private Properties properties;
    private KakaoAccount kakaoAccount;



    @ToString
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
        private String nickname;
        private String profileImage;
    }
    @ToString
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoAccount {
        private String email;
        private String gender;
    }
}
