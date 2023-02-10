package com.pickle.server.store.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class StoreOpenDay {
    @Id
    @Column(name ="store_open_day_id")
    private Long id;

    @Column
    private Boolean monday;

    @Column
    private Boolean tuesday;

    @Column
    private Boolean wednesday;

    @Column
    private Boolean thursday;

    @Column
    private Boolean friday;

    @Column
    private Boolean saturday;

    @Column
    private Boolean sunday;
}
