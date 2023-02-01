package com.pickle.server.auth.client;


import com.pickle.server.auth.dto.KakaoUserResponse;
import com.pickle.server.auth.error.TokenValidFailedException;
import com.pickle.server.config.PropertyUtil;
import com.pickle.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Log4j2
@Component
@RequiredArgsConstructor
public class ClientKakao { //카카오에서 사용자 정보 조회

    private final WebClient webClient;

    public User getUserData(String accessToken)  {//엑세스 토큰으로 유저 정보를 받아오고 그걸 유저로 만들어 변환
        try{
            KakaoUserResponse kakaoUserResponse = webClient.get()
                    .uri(new URI("https://kapi.kakao.com/v2/user/me"))
                    .headers(h -> h.setBearerAuth(accessToken))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
                    .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                    .bodyToMono(KakaoUserResponse.class)
                    .block();
            log.info(kakaoUserResponse.getKakao_account().getEmail());
            log.info(kakaoUserResponse.getProperties().getNickname());
            log.info(kakaoUserResponse.getProperties().getProfile_image());
            return User.builder()
                    .origin(SocialOrigin.KAKAO.toString())
                    .name(kakaoUserResponse.getProperties().getNickname())
                    .email(kakaoUserResponse.getKakao_account().getEmail())
                    .image(kakaoUserResponse.getProperties().getProfile_image())
                    .build();

        }
        catch (URISyntaxException e){
            throw new IllegalArgumentException();
        }
    }
}
