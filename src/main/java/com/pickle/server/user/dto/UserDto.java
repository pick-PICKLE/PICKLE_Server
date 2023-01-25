package com.pickle.server.user.dto;

import com.pickle.server.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDto {
    private Long user_id;
    private String name;
    private String image;
    private String email;
/*
    public UserDto(User user){
        this.email = user.getEmail();
        this.name = user.getName();
        this.user_id = user.getId();
        this.image = user.getImage();
    }*/
    public UserDto(User entity){
        this.user_id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.image = entity.getImage();
    }

}
