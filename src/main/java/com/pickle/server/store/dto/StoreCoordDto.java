package com.pickle.server.store.dto;

import com.pickle.server.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreCoordDto {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Double dist;

    public StoreCoordDto(Store store, Double dist) {
        this.id = store.getId();
        this.name = store.getName();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
        this.dist = dist;
    }
}
