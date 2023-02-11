package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.domain.DressReservation;
import com.pickle.server.dress.domain.ReservedDress;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.text.DecimalFormat;

@Data
@Getter
@AllArgsConstructor
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

    @ApiModelProperty(example = "의상 이미지 url")
    @JsonProperty("dress_image_url")
    private String dressImageUrl;

    @ApiModelProperty(example = "합계 가격")
    @JsonProperty("price")
    private String price;

    @QueryProjection
    public DressOrderListDto(DressReservation dressReservation, ReservedDress reservedDress, String dressImgUrl){
        DecimalFormat priceFormat = new DecimalFormat("###,###");

        this.pickUpDateTime = dressReservation.getPickUpDateTime().toString();
        this.storeName = dressReservation.getStore().getName();
        this.dressName = reservedDress.getDress().getName();
        this.dressImageUrl = reservedDress.getDress().getImageList().toString();
        this.price = priceFormat.format(dressReservation.getPrice())+"원";

    }
}
