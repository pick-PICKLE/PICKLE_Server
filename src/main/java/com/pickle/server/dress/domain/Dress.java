package com.pickle.server.dress.domain;


import com.pickle.server.common.Timestamped;
import com.pickle.server.store.domain.Store;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Dress extends Timestamped {

    @Id
    @Column(name ="dress_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String category;

    @Column
    private String name;

    @Column
    private Integer price;

    @Column
    private String comment;

    @OneToMany(mappedBy = "dress")
    private List<DressImage> imageList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToOne
    @JoinColumn(name = "dress_option1_id", nullable = false)
    private DressOption dressOption1;

    @OneToOne
    @JoinColumn(name = "dress_option2_id", nullable = false)
    private DressOption dressOption2;

    @OneToMany(mappedBy = "dress")
    private List<DressStock> dressStockList = new ArrayList<>();

}
