package com.pickle.server.auth.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //로그인 전에 타게 되는 필터
    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)  throws ServletException, IOException {


        final String authorizationHeader = request.getHeader("Authorization");
        System.out.println("로그인 시도");
        System.out.println(authorizationHeader);
         // 스웨거에선 access token이 없기에 헤더 내에 bearer로 시작하는 값이 있는지 없는지 filter로 확인 다만 현재 bearer는 미적용
        if (authorizationHeader != null ) {
            System.out.println("jwt 토큰 존재");
            String tokenStr = JwtHeaderUtil.getAccessToken(request);
            AuthToken token = tokenProvider.convertAuthToken(tokenStr);   //토큰 정보를 받아보고
            if (token.validate()) { //토큰이 유효성이 있다면
                Authentication authentication = tokenProvider.getAuthentication(tokenStr); //권한을 받은 후에
                SecurityContextHolder.getContext().setAuthentication(authentication); //권한 설정
            }
        }
        filterChain.doFilter(request, response);
        System.out.println("필터링 끝");
    }
}