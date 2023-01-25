package com.pickle.server.likes.dto;


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
    private LocalTime open_time;
    private LocalTime close_time;

    public StoreLikeDto(Long store_id,String name,String image,LocalTime open_time,LocalTime close_time){
        this.store_id = store_id;
        this.name = name;
        this.image = image;
        this.open_time = open_time;
        this.close_time = close_time;

    }
}
