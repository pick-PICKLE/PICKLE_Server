package com.pickle.server.user.controller;

import com.pickle.server.likes.dto.LikesDto;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.dto.UserDto;
import com.pickle.server.user.repository.UserRepository;
import com.pickle.server.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Autowired
    UserRepository userRepository;
 /*   @ApiOperation(value ="내 프로필 보기")
    @GetMapping(value = "users/my-profile")
    public UserDto getMyProfile(@ApiIgnore User user){
        return userService.getMyProfile(user);
    }
*/
    @GetMapping("/uu")
    public List<UserDto> findAll(){
        return userService.findAll();
    }

}
