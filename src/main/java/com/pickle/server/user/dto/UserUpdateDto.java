package com.pickle.server.user.dto;

import com.pickle.server.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDto {
    private String name;
    private String image;
    private String email;

    public UserUpdateDto(User user){
        this.email = user.getEmail();
        this.name = user.getName();
        this.image = user.getImage();
    }
}
