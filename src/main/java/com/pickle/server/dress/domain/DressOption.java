package com.pickle.server.dress.domain;

import com.pickle.server.common.Timestamped;
import com.pickle.server.store.domain.Store;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "dress_option")
public class DressOption {

    @Id
    @Column(name ="dress_option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "dressOption")
    private List<DressOptionDetail> dressOptionDetailList = new ArrayList<>();
}
