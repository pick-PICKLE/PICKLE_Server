package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.domain.*;
import com.pickle.server.store.domain.StoreOpenDay;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;


@Getter
public class DressOrderDto {
    @ApiModelProperty(example = "예약내역 id")
    @JsonProperty("reserved_dress_id")
    private Long reservedDressId;

    @ApiModelProperty(example = "스토어 이름")
    @JsonProperty("store_name")
    private String storeName;

    @ApiModelProperty(example = "스토어 주소")
    @JsonProperty("store_address")
    private String storeAddress;

    @ApiModelProperty(example = "운영 시간")
    @JsonProperty("hours_of_operation")
    private String hoursOfOperation;

    @ApiModelProperty(example = "문 여는 요일")
    @JsonProperty("store_open_day")
    private String storeOpenDay;

    @ApiModelProperty(example = "의상 이름")
    @JsonProperty("dress_name")
    private String dressName;

    @ApiModelProperty(example = "의상 대표 이미지")
    @JsonProperty("dress_image_url")
    private String dressImageUrl;

    @ApiModelProperty(example = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("pickup_datetime")
    private String pickUpDateTime;

    @ApiModelProperty(example = "드레스 픽업 요청사항")
    @JsonProperty("comment")
    private String comment;

    @ApiModelProperty(example = "합계 가격")
    @JsonProperty("price")
    private String price;

//    @ApiModelProperty(example = "드레스 옵션 1")
//    @JsonProperty("dress_option1")
//    private String dressOption1;

    @ApiModelProperty(example = "드레스 옵션 1_name")
    @JsonProperty("dress_option1_name")
    private String dressOptionName1;

//    @ApiModelProperty(example = "드레스 옵션 2")
//    @JsonProperty("dress_option2")
//    private String dressOption2;
    @ApiModelProperty(example = "드레스 옵션 2_name")
    @JsonProperty("dress_option2_name")
//    private DressOptionDetail dressOption1;
    private String dressOptionName2;

    @ApiModelProperty(example = "예약 상태")
    @JsonProperty("reservation_status")
    private String status;


    @QueryProjection
    public DressOrderDto(DressReservation dressReservation, ReservedDress reservedDress, String dressImageUrl) {
        DecimalFormat priceFormat = new DecimalFormat("###,###");

        this.reservedDressId = reservedDress.getId();
        this.storeName = dressReservation.getStore().getName();
        this.storeAddress = dressReservation.getStore().getAddress();
        this.dressName = reservedDress.getDress().getName();
        this.dressImageUrl = dressImageUrl;
        this.hoursOfOperation = dressReservation.getStore().getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "~"
                + dressReservation.getStore().getCloseTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.storeOpenDay = makeStoreOpenDayIntroduction(dressReservation.getStore().getStoreOpenDay());
        this.pickUpDateTime = dressReservation.getPickUpDateTime().toString();
//        this.dressOption1 = reservedDress.getDressOptionDetail1().getDressOption().getName();
        this.dressOptionName1 = reservedDress.getDressOptionDetail1().getName();
//        this.dressOption2 = reservedDress.getDressOptionDetail2().getDressOption().getName();
        this.dressOptionName2 = reservedDress.getDressOptionDetail2().getName();
        this.comment =dressReservation.getComment();
        this.price = priceFormat.format(dressReservation.getPrice()*reservedDress.getQuantity())+"원";
        this.status = dressReservation.getStatus();
    }

    private String makeStoreOpenDayIntroduction(StoreOpenDay storeOpenDay){
        String message = "";
        if(storeOpenDay.getMonday()) message = message.concat("월 ");
        if(storeOpenDay.getTuesday()) message = message.concat("화 ");
        if(storeOpenDay.getWednesday()) message = message.concat("수 ");
        if(storeOpenDay.getThursday()) message = message.concat("목 ");
        if(storeOpenDay.getFriday()) message = message.concat("금 ");
        if(storeOpenDay.getSaturday()) message = message.concat("토 ");
        if(storeOpenDay.getSunday()) message = message.concat("일 ");
        return message + "영업";
    }
}
