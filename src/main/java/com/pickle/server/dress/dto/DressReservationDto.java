package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DressReservationDto {

    @ApiModelProperty(example = "매장 id")
    @JsonProperty("store_id")
    private Long storeId;

    @ApiModelProperty(example = "드레스 id")
    @JsonProperty("dress_id")
    private Long dressId;

    @ApiModelProperty(example = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("pickup_datetime")
    private String pickUpDateTime;

    @JsonProperty("reserved_dress_list")
    private List<StockQuantityDto> reservedDressList = new ArrayList<>();

    @ApiModelProperty(example = "드레스 픽업 요청사항")
    @JsonProperty("comment")
    private String comment;

    @ApiModelProperty(example = "합계 가격")
    @JsonProperty("price")
    private Integer price;

}

