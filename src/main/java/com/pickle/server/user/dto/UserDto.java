package com.pickle.server.user.dto;

import com.pickle.server.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {
    private Long userId;
    private String name;
    private String image;
    private String email;

    public UserDto(User user){
        this.email = user.getEmail();
        this.name = user.getName();
        this.userId = user.getId();
        this.image = user.getImage();
    }
}
