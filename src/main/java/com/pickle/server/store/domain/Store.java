
package com.pickle.server.store.domain;


import com.pickle.server.common.Timestamped;
import com.pickle.server.dress.domain.Dress;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Store extends Timestamped {

    @Id
    @Column(name ="store_id")
    private Long id;

    @Column
    private String name;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @OneToMany(mappedBy = "store")
    private List<Dress> dresses = new ArrayList<>();
}
