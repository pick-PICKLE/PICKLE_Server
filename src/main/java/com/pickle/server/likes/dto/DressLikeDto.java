package com.pickle.server.likes.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter

@NoArgsConstructor
public class DressLikeDto {
    @ApiModelProperty(example = "의상 가격")
    @JsonProperty("dress_id")
    private Long dress_id;
    @ApiModelProperty(example = "의상 가격")
    @JsonProperty("name")
    private String name;
    @ApiModelProperty(example = "의상 가격")
    @JsonProperty("price")
    private Integer price;
    @ApiModelProperty(example = "의상 가격")
    @JsonProperty("image")
    private String image;

    public DressLikeDto(Long dress_id, String name, Integer price, String image){
        this.dress_id = dress_id;
        this.name = name;
        this.price = price;
        this.image = image;
    }
}
