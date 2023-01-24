package com.pickle.server.auth.controller;

import com.pickle.server.auth.dto.AuthRequest;
import com.pickle.server.auth.dto.AuthResponse;
import com.pickle.server.auth.service.KakaoAuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final KakaoAuthService kakaoAuthService;

    @ApiOperation(value = "카카오 로그인", notes = "카카오 엑세스 토큰을 이용하여 사용자 정보 받아 저장하고 앱의 토큰 반환")
    @PostMapping(value ="/kakao")
    public AuthResponse kakaoAuthRequest(@RequestBody AuthRequest authRequest){
        return kakaoAuthService.login(authRequest);
    }


//    @ApiOperation(value ="카카오 로그인", notes = "카카오 엑세스 토큰을 이용하여 사용자 정보 받아 저장하고 앱의 토큰 반환")
//    @PostMapping(value = "/kakao")
//    public ResponseEntity
}
