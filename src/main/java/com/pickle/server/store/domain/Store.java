
package com.pickle.server.store.domain;


import com.pickle.server.common.Timestamped;
import com.pickle.server.dress.domain.Dress;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Store extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="store_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String address;

    @Column(name = "image_url")
    private String imageUrl;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @OneToOne
    @JoinColumn(name = "store_open_day_id", nullable = false)
    private StoreOpenDay storeOpenDay;

    @OneToMany(mappedBy = "store")
    private List<Dress> dresses = new ArrayList<>();
}
