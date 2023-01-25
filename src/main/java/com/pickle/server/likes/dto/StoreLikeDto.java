package com.pickle.server.likes.dto;

import com.pickle.server.likes.domain.Likes;
import com.pickle.server.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class StoreLikeDto {
    private Long store_id;
    private String name;
    private String image;
    private String address;
    private LocalTime open_time;
    private LocalTime close_time;

 /*   public StoreLikeDto(Long store_id,String name,String image,String addresss,LocalTime open_time,LocalTime close_time){
        this.store_id = store_id;
        this.name = name;
        this.image = image;
        this.address = address;
        this.open_time = open_time;
        this.close_time = close_time;
    }*/
    public StoreLikeDto(Store store){
        this.store_id = store.getId();
        this.name = store.getName();
        this.image = store.getImage();
        this.open_time = store.getOpenTime();
        this.close_time = store.getCloseTime();
    }
}
