package com.pickle.server.auth.service;

import com.pickle.server.auth.dto.JoinRequest;
import com.pickle.server.auth.jwt.AuthToken;
import com.pickle.server.auth.jwt.AuthTokenProvider;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthTokenProvider authTokenProvider;
    @Transactional
    public JSONObject join(JoinRequest joinRequest){
        Optional<User> findUser =  userRepository.findByEmail(joinRequest.getEmail());
        AuthToken appToken = authTokenProvider.createUserAppToken(joinRequest.getEmail());
        if(!findUser.isPresent()){
            User user = User.builder()
                    .email(joinRequest.getEmail())
                    .name(joinRequest.getName())
                    .origin("소셜계정X").build();
            userRepository.save(user);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token",appToken.getToken());
        return jsonObject;
    }
}
