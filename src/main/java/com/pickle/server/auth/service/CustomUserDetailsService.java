package com.pickle.server.auth.service;

import com.pickle.server.auth.PrincipalDetails;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService { //유저의 정보를 가져오는 인터페이스 @AuthentificationPricipal을 위해 필요
    private final UserRepository userRepository;

//    스프링 세션을 사용하면 첫 로그인 시에만 loadUserByUsername메서드가 호출된
//    JWT로 구현하였다면 매 요청마다 loadUserByUsername메서드가 호출된다
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User findUser =userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        PrincipalDetails userDetail = new PrincipalDetails(findUser); //UserDetail로 변경
        return userDetail;
    }
}
