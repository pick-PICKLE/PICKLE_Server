package com.pickle.server.dress.dto;

import com.pickle.server.dress.domain.Dress;
import com.pickle.server.store.domain.Store;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class DressDto {
    private Long id;
    private String name;
    private Integer price;
    private String img;
    private String storeName;
    private LocalDateTime createdAt;

    public DressDto(Long id, String name, Integer price, String img, String storeName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.img = img;
        this.storeName = storeName;
    }
}
