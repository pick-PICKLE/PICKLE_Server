package com.pickle.server.store.dto;

import com.pickle.server.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;

    public StoreDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
    }
}
