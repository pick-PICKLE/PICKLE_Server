
package com.pickle.server.store.domain;


import com.pickle.server.common.Timestamped;
import com.pickle.server.dress.domain.Dress;
import com.pickle.server.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Store extends Timestamped {

    @Id
    @Column(name ="store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Double latitude;

    @Column
    private String image;

    @Column
    private Double longitude;

    @Column
    private String address;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @OneToMany(mappedBy = "store")
    private List<Dress> dresses = new ArrayList<>();

}
