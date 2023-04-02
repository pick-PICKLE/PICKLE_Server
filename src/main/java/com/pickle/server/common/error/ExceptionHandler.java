package com.pickle.server.common.error;

import com.pickle.server.common.util.response.BaseApiException;
import com.pickle.server.common.util.response.BaseApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler{

    @org.springframework.web.bind.annotation.ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<BaseApiException> handleCustomException(CustomException e) {
        return BaseApiException.toResponseEntity(e.getErrorResponseStatus());
    }
}
