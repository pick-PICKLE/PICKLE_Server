package com.pickle.server.dress.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "dress_option_detail")
public class DressOptionDetail {

    @Id
    @Column(name ="dress_option_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "dress_option_id", nullable = false)
    private DressOption dressOption;

}
