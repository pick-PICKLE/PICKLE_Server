package com.pickle.server.user.controller;

import com.pickle.server.user.domain.User;
import com.pickle.server.user.dto.UserDto;
import com.pickle.server.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequiredArgsConstructor
@Api(tags = "유저")
public class UserController {
    private final UserService userService;

    @ApiOperation(value ="내 프로필 보기")
    @GetMapping(value = "users/my-profile")
    public JSONObject getMyProfile(@ApiIgnore @AuthenticationPrincipal User user){
        return userService.getMyProfile(user);
    }




}
