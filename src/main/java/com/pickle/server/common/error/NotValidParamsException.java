package com.pickle.server.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "잘못된 쿼리 파라미터 값 입니다.")
public class NotValidParamsException extends IllegalArgumentException{ }
