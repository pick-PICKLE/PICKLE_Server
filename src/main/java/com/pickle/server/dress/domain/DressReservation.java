package com.pickle.server.dress.domain;

import com.pickle.server.dress.dto.DressReservationDto;
import com.pickle.server.store.domain.Store;
import com.pickle.server.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "dress_reservation")
@NoArgsConstructor
public class DressReservation {

    @Id
    @Column(name ="dress_reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "dressReservation")
    private List<DressStock> dressStockList = new ArrayList<>();

    @Column(name = "reservation_datetime")
    private LocalDateTime reservationDateTime;

    @Column
    private String comment;

    @Column
    private Integer price;

    public DressReservation(DressReservationDto dressReservationDto, User user, Store store, List<DressStock> dressStockList) {
        this.user = user;
        this.store = store;
        this.reservationDateTime = dressReservationDto.getPickUpDateTime();
        this.comment = dressReservationDto.getComment();
        this.price = dressReservationDto.getPrice();
        this.dressStockList = dressStockList;
    }
}
