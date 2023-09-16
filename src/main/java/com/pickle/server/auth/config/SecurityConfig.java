package com.pickle.server.auth.config;

import com.pickle.server.auth.jwt.AuthTokenProvider;
import com.pickle.server.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    private static final String[] AUTH_WHITELIST = {"/api/v1/auth/**","/",
            "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/index.html", "/swagger-ui.html","/webjars/**", "/swagger/**",
            "/h2-console/**", "/configuration/security","/auth/**"}; //인증된 사용자 아니어도 접근 가능
    private final AuthTokenProvider authTokenProvider;

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/static/css/**, /static/js/**, *.ico,");
//
//        // swagger
//        web.ignoring().antMatchers(
//                AUTH_WHITELIST);
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
//        http.cors().disable();

        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll() //이상하게 여기 .antMatchers는 동작하지 않음
                .anyRequest().authenticated(); //이것들을 제외하곤 인증

        http.headers().frameOptions().disable(); // h2-console 화면을 사용하기 위해 해당 옵션 disable

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(authTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .httpBasic().disable()
                .formLogin().disable()
                .logout()
                .logoutSuccessUrl("/");
    }

//    @Bean
//    public WebSecurityCustomizer configure(){
//        return (web)->web.ignoring().antMatchers(AUTH_WHITELIST);
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ //리소스 접근 권한,인증실패시 이동하는 곳 지정,csrf,cors,exceoption handling
//        JwtAuthenticationFilter jwtAuthFilter = new JwtAuthenticationFilter(authTokenProvider);
//        http
//                .csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll() //preflight 대응
//                .antMatchers("/auth/**").permitAll()
////                .anyRequest().authenticated() //위에것들 빼고는 무조건 인증을 해야함
//                .and().headers().frameOptions().disable() //h2-console 화면을 사용하기 위한 해당 옵션 disable
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 세션 미사용
//                .and().addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); //필터 적용
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

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
