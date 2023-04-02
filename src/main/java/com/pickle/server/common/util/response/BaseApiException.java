package com.pickle.server.common.util.response;

import com.pickle.server.common.error.ErrorResponseStatus;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BaseApiException {

    private Integer status;
    private String message;

    public static ResponseEntity<BaseApiException> toResponseEntity(ErrorResponseStatus errorResponseStatus) {
        return ResponseEntity
                .status(errorResponseStatus.getHttpStatus())
                .body(BaseApiException.builder()
                        .status(errorResponseStatus.getHttpStatus().value())
                        .message(errorResponseStatus.getMessage())
                        .build()
                );
    }

}