package com.pickle.server.auth.client;


import com.pickle.server.auth.dto.KakaoUserResponse;
import com.pickle.server.auth.error.TokenValidFailedException;
import com.pickle.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class ClientKakao { //카카오에서 사용자 정보 조회

    private final WebClient webClient;

    public User getUserData(String accessToken){//엑세스 토큰으로 유저 정보를 받아오고 그걸 유저로 만들어 변환
        KakaoUserResponse kakaoUserResponse = webClient.get()
                .uri("https:/kapi.kakao.com/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                .bodyToMono(KakaoUserResponse.class)
                .block();

        return User.builder()
                .origin(String.valueOf(kakaoUserResponse.getId()))
                .name(kakaoUserResponse.getProperties().getNickname())
                .email(kakaoUserResponse.getKakaoAccount().getEmail())
                .image(kakaoUserResponse.getProperties().getProfileImage())
                .build();
    }
}
