package com.pickle.server.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.store.domain.Store;
import com.pickle.server.store.domain.StoreOpenDay;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreCoordDto {
    @ApiModelProperty(example = "스토어 id")
    @JsonProperty("store_id")
    private Long storeId;
    @ApiModelProperty(example = "매장명")
    @JsonProperty("store_name")
    private String name;
    @ApiModelProperty(example = "매장 대표 사진")
    @JsonProperty("store_img")
    private String imgUrl;
    @ApiModelProperty(example = "매장 주소")
    @JsonProperty("address")
    private String address;
    @ApiModelProperty(example = "운영 시간")
    @JsonProperty("hoursOfOperation")
    private String hoursOfOperation;
    @ApiModelProperty(example = "운영 요일")
    @JsonProperty("open_day")
    private String storeOpenDay;
    @ApiModelProperty(example = "위도")
    @JsonProperty("latitude")
    private Double latitude;
    @ApiModelProperty(example = "경도")
    @JsonProperty("longitude")
    private Double longitude;
    @ApiModelProperty(example = "매장 좋아요 여부")
    @JsonProperty("store_like")
    private Boolean storeLike;

    @QueryProjection
    public StoreCoordDto(Store store, Long storeLikeId) {
        this.storeId = store.getId();
        this.name = store.getName();
        this.imgUrl = store.getImageUrl();
        this.address = store.getAddress();
        this.hoursOfOperation = store.getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "~"
                + store.getCloseTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.storeOpenDay = makeStoreOpenDayIntroduction(store.getStoreOpenDay());
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
        this.storeLike = storeLike(storeLikeId);
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

    private Boolean storeLike(Long storeLikeId) {
        if(storeLikeId == null) return false;
        else return true;
    }
}
