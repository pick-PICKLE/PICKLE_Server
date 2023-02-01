package com.pickle.server.auth.service;

import com.pickle.server.auth.jwt.AuthToken;
import com.pickle.server.auth.jwt.AuthTokenProvider;
import com.pickle.server.auth.client.ClientKakao;
import com.pickle.server.auth.dto.AuthRequest;
import com.pickle.server.auth.dto.AuthResponse;
import com.pickle.server.config.PropertyUtil;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoAuthService {
    private final ClientKakao clientKakao;
    private final UserRepository userRepository;
    private final AuthTokenProvider authTokenProvider;
    //필터 이후에 일어날 일
    public AuthResponse login(AuthRequest authRequest)
    {
        User kakaoUser = clientKakao.getUserData(authRequest.getAccessToken());
        Optional<User> user = userRepository.findByEmail(kakaoUser.getEmail());
        AuthToken appToken = authTokenProvider.createUserAppToken(kakaoUser.getEmail()); //이메일로 토큰을 만든다 (새로운 유저든 아니든 만든다)
        if(!user.isPresent()){ //유저가 없다면
            userRepository.save(kakaoUser); //새로 만든다
        }

        //추후에 이메일 중복또한 만들어야함
        return AuthResponse.builder()
                .appToken(appToken.getToken()) //프론트에 토큰 반환
                .build();
    }
}
