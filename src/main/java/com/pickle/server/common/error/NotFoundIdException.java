package com.pickle.server.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "해당 id에 해당하는 데이터가 없습니다.")
public class NotFoundIdException extends RuntimeException{ }
