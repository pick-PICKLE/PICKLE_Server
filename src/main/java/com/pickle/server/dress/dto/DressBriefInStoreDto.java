package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @QueryProjection
    public DressBriefInStoreDto(Long dressId, String dressName, String dressImageUrl, Integer dressPrice) {
        DecimalFormat priceKRWFormat  = new DecimalFormat("###,###");
        this.dressId = dressId;
        this.dressName = dressName;
        this.dressImageUrl = dressImageUrl;
        this.dressPrice = priceKRWFormat.format(dressPrice) + "원";
    }
}
