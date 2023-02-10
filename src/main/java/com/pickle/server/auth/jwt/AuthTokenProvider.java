package com.pickle.server.auth.jwt;

import com.pickle.server.auth.PrincipalDetails;
import com.pickle.server.auth.RoleType;
import com.pickle.server.auth.error.TokenValidFailedException;
import com.pickle.server.auth.service.CustomUserDetailsService;
import com.sun.istack.NotNull;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component

public class AuthTokenProvider {

    private Long expiry = 7*48*30*60*1000L; // 토큰 유효시간 일주일

    private final CustomUserDetailsService userDetailsService;
    private final String key;
    private static final String AUTHORITIES_KEY = "role";

    public AuthTokenProvider(CustomUserDetailsService userDetailsService,  @Value("${app.auth.tokenSecret}") String secretKey) {
        this.userDetailsService = userDetailsService;  //String 또는 인코딩된 byte개열 비밀키를 가지고 있으면 변형해야함
        this.key = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public AuthToken createToken(String id, RoleType roleType, Long expiry) {  //토큰 생성
        Date expiryDate = getExpiryDate(expiry);
        return new AuthToken(id, expiryDate,roleType, key);
    }

    public AuthToken createUserAppToken(String id) { //소셜로그인으로 받아온 Id
        return createToken(id,RoleType.USER, expiry);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public static Date getExpiryDate(Long expiry) {
        return new Date(System.currentTimeMillis() + expiry);
    }

    //토큰에서 유저 정보 추출(현재는 email)
    public String getUserPk(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }
    public Authentication getAuthentication(@NotNull String authToken) {
        PrincipalDetails userDetails = (PrincipalDetails) userDetailsService.loadUserByUsername(this.getUserPk(authToken));
        // userDetail을 받은 후
        System.out.println(userDetails.getUser().getEmail()); //유저 이메일 확인
        return new UsernamePasswordAuthenticationToken(userDetails.getUser(),"",userDetails.getAuthorities());
        //요기 첫 파라미터가 @AuthentificationPrincipal과 직결됨 (user로 넣어줌)
//        if(authToken.validate()) {
//
//            Claims claims = authToken.getTokenClaims();
//            Collection<? extends GrantedAuthority> authorities =
//                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
//                            .map(SimpleGrantedAuthority::new)
//                            .collect(Collectors.toList());
//
//            User principal = new User(claims.getSubject(), "", authorities);
//
//            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
//        } else {
//            throw new TokenValidFailedException();
//        }
    }
}
