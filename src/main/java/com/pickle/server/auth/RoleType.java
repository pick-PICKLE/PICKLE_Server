package com.pickle.server.auth;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RoleType {

    USER("ROLE_USER", "일반 사용자 권한"),
    ADMIN("ROLE_ADMIN", "관리자 권한"),
    NONE("NONE", "권한 없음");
    private final String code;
    private final String name;

    RoleType(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
