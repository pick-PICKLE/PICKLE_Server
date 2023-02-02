package com.pickle.server.dress.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "dress_stock")
public class DressStock {

    @Id
    @Column(name ="dress_stock_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "dress_option_detail1_id", nullable = false)
    private DressOptionDetail dressOptionDetail1;

    @OneToOne
    @JoinColumn(name = "dress_option_detail2_id", nullable = false)
    private DressOptionDetail dressOptionDetail2;

    @ManyToOne
    @JoinColumn(name = "dress_id", nullable = false)
    private Dress dress;

    @Column
    private Integer stock;

}