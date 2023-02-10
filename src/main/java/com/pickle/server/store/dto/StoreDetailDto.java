package com.pickle.server.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.dto.DressBriefInStoreDto;
import com.pickle.server.store.domain.Store;
import com.pickle.server.store.domain.StoreOpenDay;
import io.swagger.annotations.ApiModelProperty;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class StoreDetailDto {

    @ApiModelProperty(example = "스토어 id")
    @JsonProperty
    private Long storeId;

    @ApiModelProperty(example = "스토어 이름")
    @JsonProperty("store_name")
    private String storeName;

    @ApiModelProperty(example = "스토어 이미지 url")
    @JsonProperty("store_image_url")
    private String storeImageUrl;

    @ApiModelProperty(example = "스토어 주소")
    @JsonProperty("store_address")
    private String storeAddress;

    @ApiModelProperty(example = "운영 시간")
    @JsonProperty("hours_of_operation")
    private String hoursOfOperation;

    @ApiModelProperty(example = "문 여는 요일")
    @JsonProperty("store_open_day")
    private String storeOpenDay;

    @ApiModelProperty(example = "좋아요 여부")
    @JsonProperty("is_liked")
    private Boolean isLiked;

    @ApiModelProperty(example = "스토어 의상 목록")
    @JsonProperty("store_dress_list")
    private List<DressBriefInStoreDto> storeDressList;

    public StoreDetailDto(Store store, List<DressBriefInStoreDto> dressBriefInStoreDtoList, Boolean isLiked) {
        this.storeId = store.getId();
        this.storeName = store.getName();
        this.storeAddress = store.getAddress();
        this.storeImageUrl = store.getImageUrl();
        this.hoursOfOperation = store.getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "~"
                + store.getCloseTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.storeOpenDay = makeStoreOpenDayIntroduction(store.getStoreOpenDay());
        this.storeDressList = dressBriefInStoreDtoList;
        this.isLiked = isLiked;
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
