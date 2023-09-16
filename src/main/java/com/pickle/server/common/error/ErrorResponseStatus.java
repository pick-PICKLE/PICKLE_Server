package com.pickle.server.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorResponseStatus {

    BAD_REQUEST_INVALID_SEARCH_PARAMETER(BAD_REQUEST, "잘못된 검색 파라미터 값입니다."),
    BAD_REQUEST_INVALID_OPTION(BAD_REQUEST, "유효하지 않은 옵션입니다."),
    NOT_FOUND_USER_ID(NOT_FOUND, "없는 유저 ID 입니다."),
    NOT_FOUND_DRESS_ID(NOT_FOUND, "없는 의상 ID 입니다."),
    NOT_FOUND_STORE_ID(NOT_FOUND, "없는 매장 ID 입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
