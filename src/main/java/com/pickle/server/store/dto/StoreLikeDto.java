package com.pickle.server.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.store.domain.Store;
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

    @ApiModelProperty(example = "오픈 시간")
    @JsonProperty("open_time")
    private LocalTime open_time;

    @ApiModelProperty(example = "클로즈 시간")
    @JsonProperty("close_time")
    private LocalTime close_time;

    public StoreLikeDto(Store store){
        this.storeId = store.getId();
        this.name = store.getName();
        this.image = store.getImage();
        this.address = store.getAddress();
        this.open_time = store.getOpenTime();
        this.close_time = store.getCloseTime();
    }
}
