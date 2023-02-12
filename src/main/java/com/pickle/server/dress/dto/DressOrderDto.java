package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.domain.*;
import com.pickle.server.store.domain.StoreOpenDay;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;


@Data
@Getter

@NoArgsConstructor
@Setter
public class DressOrderDto {
    @ApiModelProperty(example = "예약내역 id")
    @JsonProperty("reserved_dress_id")
    private Long reservedDressId;
    @ApiModelProperty(example = "스토어 id")
    @JsonProperty("store_id")
    private Long storeId;

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

    @ApiModelProperty(example = "의상 이미지 url")
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

    @ApiModelProperty(example = "드레스 옵션 1")
    @JsonProperty("dress_option1")
    private DressOptionDetail dressOption1;

    @ApiModelProperty(example = "드레스 옵션 2")
    @JsonProperty("dress_option2")
    private DressOptionDetail dressOption2;

    @ApiModelProperty(example = "quantity")
    @JsonProperty("quantity")
    private Integer quantity;




  //  @QueryProjection
//    public DressOrderDto(DressReservation dressReservation, DressReservationDto dressReservationDto,String dressImgUrl){
//        DecimalFormat priceFormat = new DecimalFormat("###,###");
//
//        this.storeName = dressReservation.getStore().getName();
//        this.storeAddress = dressReservation.getStore().getAddress();
//        this.pickUpDateTime = dressReservationDto.getPickUpDateTime();
//        this.dressImageUrl = getDressImageUrl();
//
//        this.hoursOfOperation = dressReservation.getStore().getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "~"
//                + dressReservation.getStore().getCloseTime().format(DateTimeFormatter.ofPattern("HH:mm"));
//        this.storeOpenDay = makeStoreOpenDayIntroduction(dressReservation.getStore().getStoreOpenDay());
//        this.comment = dressReservationDto.getComment();
//        this.price = priceFormat.format(dressReservation.getPrice())+"원";
//        this.dressOption1 = dressReservationDto.getReservedDressList().toString();
//        this.dressOption2 = dressReservationDto.getReservedDressList().toString();
//
//
 //   }
    @QueryProjection
    public DressOrderDto(DressReservation dressReservation, ReservedDress reservedDress, String dressImageUrl/*, DressStock dressStock*/) {
        DecimalFormat priceFormat = new DecimalFormat("###,###");

        this.reservedDressId = reservedDress.getId();
        this.storeId = dressReservation.getStore().getId(); //필요x 삭제
        this.storeName = dressReservation.getStore().getName();
        this.storeAddress = dressReservation.getStore().getAddress();
        this.dressName = reservedDress.getDress().getName();
        this.dressImageUrl = reservedDress.getDress().getImageList().toString();
        this.hoursOfOperation = dressReservation.getStore().getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "~"
                + dressReservation.getStore().getCloseTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.storeOpenDay = makeStoreOpenDayIntroduction(dressReservation.getStore().getStoreOpenDay());
        this.pickUpDateTime = dressReservation.getPickUpDateTime().toString();
//
//        this.dressOption1 = dressStock.getDressOptionDetail1();
//        this.dressOption2 = dressStock.getDressOptionDetail2();
        this.comment =dressReservation.getComment();
        this.price = priceFormat.format(dressReservation.getPrice())+"원";
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
