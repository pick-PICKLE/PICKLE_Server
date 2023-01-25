package com.pickle.server.likes.dto;


import lombok.Getter;


@Getter
public class DressLikeDto {
    private Long dress_id;
    private String name;
    private Integer price;
    private String image;

    public DressLikeDto(Long dress_id, String name, Integer price, String image){
        this.dress_id = dress_id;
        this.name = name;
        this.price = price;
        this.image = image;
    }
}
