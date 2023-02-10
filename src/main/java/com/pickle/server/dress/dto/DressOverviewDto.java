package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.domain.Dress;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.text.DecimalFormat;

@Data
public class DressOverviewDto {
    @ApiModelProperty(example = "스토어 id")
    @JsonProperty("store_id")
    private Long storeId;

    @ApiModelProperty(example = "스토어 이름")
    @JsonProperty("store_name")
    private String storeName;

    @ApiModelProperty(example = "의상 id")
    @JsonProperty("dress_id")
    private Long dressId;

    @ApiModelProperty(example = "의상 이름")
    @JsonProperty("dress_name")
    private String dressName;

    @ApiModelProperty(example = "의상 대표 사진")
    @JsonProperty("dress_default_img")
    private String dressDefaultImg;

    @ApiModelProperty(example = "의상 가격")
    @JsonProperty("dress_price")
    private String dressPrice;

    @ApiModelProperty(example = "의상 좋아요 여부")
    @JsonProperty("dress_like")
    private Boolean dressLike;

    @QueryProjection
    public DressOverviewDto(Dress dress, Long dressLikeId, String dressImgUrl) {
        this.storeId = dress.getStore().getId();
        this.storeName = dress.getStore().getName();
        this.dressId = dress.getId();
        this.dressName = dress.getName();
        this.dressDefaultImg = dressImgUrl;

        DecimalFormat priceKRWFormat  = new DecimalFormat("###,###");
        this.dressPrice = priceKRWFormat.format(dress.getPrice()) + "원";
        this.dressLike = dressLike(dressLikeId);
    }

    private Boolean dressLike(Long dressLikeId) {
        if(dressLikeId == null) return false;
        else return true;
    }
}
