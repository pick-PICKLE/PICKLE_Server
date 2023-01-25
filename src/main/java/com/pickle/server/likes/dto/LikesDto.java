package com.pickle.server.likes.dto;

import com.pickle.server.likes.domain.Likes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
//좋아요-의상 test
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LikesDto {
    private Long dress_id;
    private Long like_id;
    private Long user_id;
    public LikesDto(Likes entity){
        this.dress_id = entity.getDress().getId();
        this.user_id = entity.getUser().getId();
        this.like_id = entity.getId();
      //  this.like_id = getLike_id();
    }
/*
    public LikesDTO(Long dress_id,Long like_id,Long user_id) {
        this.dress_id = dress_id;
        this.like_id = like_id;
        this.user_id = user_id;
    }*/
}
