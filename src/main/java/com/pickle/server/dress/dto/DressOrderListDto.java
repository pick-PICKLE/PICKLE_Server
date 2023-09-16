package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.domain.DressReservation;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;


@Getter
public class DressOrderListDto {
    @ApiModelProperty(example = "주문 번호 id")
    @JsonProperty("dress_reservation_id")
    private Long dressReservationId;

    @ApiModelProperty(example = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("order_time")
    private String orderTime;

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

    @ApiModelProperty(example = "예약 상태")
    @JsonProperty("reservation_status")
    private String status;

    @QueryProjection
    public DressOrderListDto(DressReservation dressReservation, String urlHead) {
        DecimalFormat priceFormat = new DecimalFormat("###,###");
        this.dressReservationId = dressReservation.getId();
        this.orderTime = dressReservation.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.storeName = dressReservation.getStore().getName();
        this.dressName = dressReservation.getReservedDressList().get(0).getDress().getName();
        this.dressImageUrl = urlHead + dressReservation.getReservedDressList().get(0).getDress().getImageList().get(0).getImageUrl();
        this.price = priceFormat.format(dressReservation.getPrice()) + "원";
        this.status = dressReservation.getStatus();
    }
}
