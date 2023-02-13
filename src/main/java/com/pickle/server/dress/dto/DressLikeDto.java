package com.pickle.server.dress.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.domain.DressImage;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@Data
@Getter
@NoArgsConstructor
@Setter
public class DressLikeDto {
    @ApiModelProperty(example = "의상 id")
    @JsonProperty("dress_id")
    private Long dressId;
    @ApiModelProperty(example = "의상 이름")
    @JsonProperty("dress_name")
    private String dressName;
    @ApiModelProperty(example = "의상 가격")
    @JsonProperty("price")
    private String price;

    @ApiModelProperty(example = "의상 이미지")
    @JsonProperty("image")
    private String image;

    @ApiModelProperty(example = "스토어 이름")
    @JsonProperty("store_name")
    private String storeName;

    @QueryProjection
    public DressLikeDto(Dress dress, String imageUrl){
        DecimalFormat priceFormat = new DecimalFormat("###,###");
        this.storeName = dress.getStore().getName();
        this.dressId = dress.getId();
        this.dressName = dress.getName();
        this.price = priceFormat.format(dress.getPrice())+"원";
        this.image = imageUrl;
    }
}
