package com.pickle.server.common.util.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorResponseStatus {

    SAMPLE(HttpStatus.BAD_REQUEST, "잘못된 태그");

    private final HttpStatus code;
    private final String message;
}
