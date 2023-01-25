package com.pickle.server.auth.jwt;

import com.pickle.server.auth.RoleType;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
@Slf4j
@RequiredArgsConstructor
public class AuthToken {
    @Getter
    private final String token;
    private final String key;

    private static final String AUTHORITIES_KEY = "role";

    AuthToken(String socialId, Date expiry,RoleType roleType, String key) {
        this.key = key;
        this.token = createAuthToken(socialId,roleType.toString(), expiry);
    }

    private String createAuthToken(String socialId, String role, Date expiry) {
        Claims claims = Jwts.claims().setSubject(socialId); //payload에 저장되는 정보 단위
        claims.put(AUTHORITIES_KEY,role);
        return Jwts.builder()
                .setSubject(socialId) //토큰 제목
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,key)
                .setExpiration(expiry) //토큰 기간
                .compact();
    }

    public boolean validate() {
        return this.getTokenClaims();
    }

    public boolean getTokenClaims() {
        try {
            Jws<Claims> claims= Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) { //토큰 만료
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) { //claims가 비어있는 경우
            log.info("JWT token compact of handler are invalid.");
        }
        return true;
    }
}
