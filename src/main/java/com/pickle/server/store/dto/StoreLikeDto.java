package com.pickle.server.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.store.domain.Store;
import com.pickle.server.store.domain.StoreOpenDay;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class StoreLikeDto {
    @ApiModelProperty(example = "스토어 id")
    @JsonProperty("store_id")
    private Long storeId;
    @ApiModelProperty(example = "스토어 이름")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(example = "스토어 이미지")
    @JsonProperty("image")
    private String image;

    @ApiModelProperty(example = "스토어 주소")
    @JsonProperty("address")
    private String address;

    @ApiModelProperty(example = "문 여는 요일")
    @JsonProperty("store_open_day")
    private String storeOpenDay;
    @ApiModelProperty(example = "오픈 시간")
    @JsonProperty("open_time")
    private LocalTime open_time;

    @ApiModelProperty(example = "클로즈 시간")
    @JsonProperty("close_time")
    private LocalTime close_time;

    public StoreLikeDto(Store store){
        this.storeId = store.getId();
        this.name = store.getName();
        this.image = store.getImageUrl();
        this.address = store.getAddress();
        this.storeOpenDay = makeStoreOpenDayIntroduction(store.getStoreOpenDay());
        this.open_time = store.getOpenTime();
        this.close_time = store.getCloseTime();
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
