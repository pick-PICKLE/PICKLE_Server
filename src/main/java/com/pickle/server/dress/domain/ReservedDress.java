package com.pickle.server.dress.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "reserved_dress")
@NoArgsConstructor
public class ReservedDress {

    @Id
    @Column(name ="reserved_dress_id")
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

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private DressReservation dressReservation;

    @Column
    private Integer quantity;

    public ReservedDress(DressOptionDetail dressOptionDetail1, DressOptionDetail dressOptionDetail2, Dress dress, Integer quantity, DressReservation dressReservation) {
        this.dressOptionDetail1 = dressOptionDetail1;
        this.dressOptionDetail2 = dressOptionDetail2;
        this.dress = dress;
        this.quantity = quantity;
        this.dressReservation = dressReservation;
    }

}