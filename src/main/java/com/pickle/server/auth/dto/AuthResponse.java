package com.pickle.server.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse { //처음에 받게될 엑세스 토큰
    private String appToken;
    private Boolean isNewMember;
}
