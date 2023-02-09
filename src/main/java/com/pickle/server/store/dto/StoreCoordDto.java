package com.pickle.server.store.dto;

import com.pickle.server.store.domain.Store;
import com.pickle.server.store.domain.StoreOpenDay;
import com.querydsl.core.annotations.QueryProjection;
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
    private Long storeId;
    private String name;
    private String imgUrl;
    private String address;
    private String hoursOfOperation;
    private String storeOpenDay;
    private Double latitude;
    private Double longitude;
    private Long storeLikeId;

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
        this.storeLikeId = storeLikeId;
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
