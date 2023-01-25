package com.pickle.server.likes.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.domain.Dress;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter

@NoArgsConstructor
public class DressLikeDto {
    @ApiModelProperty(example = "의상 가격")
    @JsonProperty("dress_id")
    private Long dressId;
    @ApiModelProperty(example = "의상 가격")
    @JsonProperty("name")
    private String name;
    @ApiModelProperty(example = "의상 가격")
    @JsonProperty("price")
    private Integer price;
    @ApiModelProperty(example = "의상 가격")
    @JsonProperty("image")
    private String image;

    public DressLikeDto(Dress dress){
        this.dressId = dress.getId();
        this.name = dress.getName();
        this.price = dress.getPrice();
        this.image = dress.getImage();
    }
}
