package com.pickle.server.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class JoinRequest {

    @ApiModelProperty(example = "이름")
    private String name;
    @ApiModelProperty(example = "이메일")
    private String email;
}
