package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.domain.DressReservation;
import com.pickle.server.dress.domain.ReservedDress;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;

import java.text.DecimalFormat;


@Getter
public class DressOrderListDto {
//    @ApiModelProperty(example = "예약내역 id")
//    @JsonProperty("reserved_dress_id")
//    private Long reservedDressId;

    @ApiModelProperty(example = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("pickup_datetime")
    private String pickUpDateTime;

    @ApiModelProperty(example = "스토어 이름")
    @JsonProperty("store_name")
    private String storeName;

    @ApiModelProperty(example = "의상 이름")
    @JsonProperty("dress_name")
    private String dressName;

    @ApiModelProperty(example = "의상 대표 이미지")
    @JsonProperty("dress_image_url")
    private String dressImageUrl;

    @ApiModelProperty(example = "합계 가격")
    @JsonProperty("price")
    private String price;

    @QueryProjection
    public DressOrderListDto(DressReservation dressReservation, ReservedDress reservedDress, String dressImageUrl){
        DecimalFormat priceFormat = new DecimalFormat("###,###");

        this.pickUpDateTime = dressReservation.getPickUpDateTime().toString();
        this.storeName = dressReservation.getStore().getName();
        this.dressName = reservedDress.getDress().getName();
        this.dressImageUrl = dressImageUrl;
        this.price = priceFormat.format(dressReservation.getPrice())+"원";
    }
}
