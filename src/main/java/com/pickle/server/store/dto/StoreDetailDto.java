package com.pickle.server.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.dto.DressBriefDto;
import com.pickle.server.store.domain.Store;
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

//    @ApiModelProperty(example = "좋아요 여부")
//    @JsonProperty("is_liked")
//    private Boolean isLiked;

    @ApiModelProperty(example = "스토어 의상 목록")
    @JsonProperty("store_dress_list")
    private List<DressBriefDto> storeDressList;

    public StoreDetailDto(Store store, List<DressBriefDto> dressBriefDtoList, String urlHead) {
        this.storeId = store.getId();
        this.storeName = store.getName();
        this.storeAddress = store.getAddress();
        this.storeImageUrl = urlHead + store.getImage();
        this.hoursOfOperation = store.getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "~" + store.getCloseTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.storeDressList = dressBriefDtoList;
    }
}
