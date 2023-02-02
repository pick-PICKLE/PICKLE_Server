package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.store.domain.Store;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DressReservationFormDto {

    @ApiModelProperty(example = "스토어 id")
    @JsonProperty("store_id")
    private Long storeId;

    @ApiModelProperty(example = "스토어 이름")
    @JsonProperty("store_name")
    private String storeName;

    @ApiModelProperty(example = "스토어 운영 시간")
    @JsonProperty("hours_of_operation")
    private String hoursOfOperation;

    @ApiModelProperty(example = "픽업 가능한 시간")
    @JsonProperty("pickup_time")
    private List<String> pickUpTimeList;

    public DressReservationFormDto(Store store) {
        this.storeId = store.getId();
        this.storeName = store.getName();
        this.hoursOfOperation = store.getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "~"
                + store.getCloseTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.pickUpTimeList = getAllAvaliableTime(store.getOpenTime(), store.getCloseTime());
    }

    private List<String> getAllAvaliableTime(LocalTime openTime, LocalTime closeTime) {
        List<String> pickUpTimeList = new ArrayList<>();
        for(LocalTime time = openTime ; time.compareTo(closeTime) != 0; time = time.plusMinutes(30)){
            pickUpTimeList.add(time.format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        return pickUpTimeList;
    }
}
