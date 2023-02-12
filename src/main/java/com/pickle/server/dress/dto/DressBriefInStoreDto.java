package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.domain.DressLike;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.text.DecimalFormat;


@Data
public class DressBriefInStoreDto {

    @ApiModelProperty(example = "의상 id")
    @JsonProperty("dress_id")
    private Long dressId;

    @ApiModelProperty(example = "의상 이름")
    @JsonProperty("dress_name")
    private String dressName;


    @ApiModelProperty(example = "의상 이미지 url")
    @JsonProperty("dress_image_url")
    private String dressImageUrl;

    @ApiModelProperty(example = "의상 가격")
    @JsonProperty("dress_price")
    private String dressPrice;

    @ApiModelProperty(example = "좋아요 여부")
    @JsonProperty("is_liked")
    private Boolean isLiked;

    @QueryProjection
    public DressBriefInStoreDto(Long dressId, String dressName, String dressImageUrl, Integer dressPrice, DressLike dressLike) {
        DecimalFormat priceKRWFormat  = new DecimalFormat("###,###");
        this.dressId = dressId;
        this.dressName = dressName;
        this.dressImageUrl = dressImageUrl;
        this.dressPrice = priceKRWFormat.format(dressPrice) + "원";
        this.isLiked = (dressLike != null) ? true : false;
    }
}
