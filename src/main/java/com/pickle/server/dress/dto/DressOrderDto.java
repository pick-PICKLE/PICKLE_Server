package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.domain.*;
import com.pickle.server.store.domain.StoreOpenDay;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Getter
public class DressOrderDto {
    @ApiModelProperty(example = "주문 번호")
    @JsonProperty("dress_reservation_id")
    private Long dressReservationId;

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

    @ApiModelProperty(example = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("pickup_datetime")
    private String pickUpDateTime;

    @ApiModelProperty(example = "드레스 픽업 요청사항")
    @JsonProperty("comment")
    private String comment;

    @ApiModelProperty(example = "합계 가격")
    @JsonProperty("price")
    private String price;

    @ApiModelProperty(example = "예약 상태")
    @JsonProperty("reservation_status")
    private String status;

    private List<ReservedDressDto> reservedDressList = new ArrayList<>();


    @QueryProjection
    public DressOrderDto(DressReservation dressReservation, String urlHead) {
        DecimalFormat priceFormat = new DecimalFormat("###,###");

        this.dressReservationId = dressReservation.getId();
        this.storeName = dressReservation.getStore().getName();
        this.storeAddress = dressReservation.getStore().getAddress();
        this.hoursOfOperation = dressReservation.getStore().getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "~"
                + dressReservation.getStore().getCloseTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.storeOpenDay = makeStoreOpenDayIntroduction(dressReservation.getStore().getStoreOpenDay());
        this.pickUpDateTime = dressReservation.getPickUpDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " / "
                + dressReservation.getPickUpDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        for(ReservedDress rd : dressReservation.getReservedDressList()){
            this.reservedDressList.add(new ReservedDressDto(rd, urlHead));
        }
        this.comment = dressReservation.getComment();
        this.price = priceFormat.format(dressReservation.getPrice())+"원";
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
