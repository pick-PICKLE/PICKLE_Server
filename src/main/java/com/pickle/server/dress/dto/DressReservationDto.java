package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;
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

    @ApiModelProperty(example = "드레스 픽업 날짜,시간")
    @JsonProperty("dress_datetime")
    private LocalDateTime pickUpDateTime;

    @JsonProperty("reserved_dress_list")
    private List<StockQuantityDto> reservedDressList = new ArrayList<>();

    @ApiModelProperty(example = "드레스 픽업 요청사항")
    @JsonProperty("dress_reservation_comment")
    private String comment;


    @ApiModelProperty(example = "합계 가격")
    @JsonProperty("price")
    private Integer price;
}

