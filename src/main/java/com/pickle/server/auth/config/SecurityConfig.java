package com.pickle.server.auth.config;

import com.pickle.server.auth.jwt.AuthTokenProvider;
import com.pickle.server.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] AUTH_WHITELIST = {"/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**"};
    private final AuthTokenProvider authTokenProvider;
    @Bean
    public WebSecurityCustomizer configure(){
        return (web)->web.ignoring().antMatchers(AUTH_WHITELIST);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ //리소스 접근 권한,인증실패시 이동하는 곳 지정,csrf,cors,exceoption handling
        JwtAuthenticationFilter jwtAuthFilter = new JwtAuthenticationFilter(authTokenProvider);
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll() //preflight 대응
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated() //위에것들 빼고는 무조건 인증을 해야함
                .and().headers()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 세션 미사용
                .and().addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); //필터 적용
        return http.build();
    }

    //Override 이전방식-> Bean등록으로 새로운 방식
//    @Override
//    public void configure(WebSecurity web) throws Exception{
//        web.ignoring().antMatchers(AUTH_WHITELIST);
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws  Exception{
//
//    }


}
