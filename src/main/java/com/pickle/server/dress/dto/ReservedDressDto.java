package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.domain.*;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.text.DecimalFormat;

@Getter
public class ReservedDressDto {
    @ApiModelProperty(example = "예약한 아이템 id")
    @JsonProperty("reserved_dress_id")
    Long reservedDressId;
    @ApiModelProperty(example = "선택한 옵션")
    @JsonProperty("dress_option_detail")
    String dressOptionDetail;
    @ApiModelProperty(example = "옷 이름")
    @JsonProperty("dress_name")
    String dressName;
    @ApiModelProperty(example = "옷 이미지")
    @JsonProperty("dress_imgurl")
    String dressImgUrl;
    @ApiModelProperty(example = "선택된 옵션의 가격")
    @JsonProperty("select_price")
    String selectPrice;


    public ReservedDressDto(ReservedDress reservedDress, String urlhead) {
        DecimalFormat priceFormat = new DecimalFormat("###,###");

        this.reservedDressId = reservedDress.getId();
        this.dressOptionDetail = getDressOptionDto(reservedDress.getDressOptionDetail1(), reservedDress.getDressOptionDetail2(), reservedDress.getQuantity());
        this.dressName = reservedDress.getDress().getName();
        this.selectPrice = priceFormat.format(reservedDress.getDress().getPrice() * reservedDress.getQuantity())+"원";
        this.dressImgUrl = urlhead + reservedDress.getDress().getImageList().get(0).getImageUrl();
    }

    private String getDressOptionDto(DressOptionDetail dressOptionDetail1, DressOptionDetail dressOptionDetail2, Integer quantity) {
        String selectOption = dressOptionDetail1.getName() + " / " + dressOptionDetail2.getName() + " " + quantity.toString() + "개";

        return selectOption;
    }
}
