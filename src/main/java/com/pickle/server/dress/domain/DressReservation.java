package com.pickle.server.dress.domain;

import com.pickle.server.common.Timestamped;
import com.pickle.server.dress.dto.DressReservationDto;
import com.pickle.server.store.domain.Store;
import com.pickle.server.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "dress_reservation")
@NoArgsConstructor
public class DressReservation extends Timestamped {

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
    private List<ReservedDress> reservedDressList = new ArrayList<>();

    @Column(name = "pickup_datetime")
    private LocalDateTime pickUpDateTime;

    @Column
    private String comment;

    @Column
    private Integer price;

    @Column
    private String status;

    public DressReservation(DressReservationDto dressReservationDto, User user, Store store, List<ReservedDress> reservedDressList) {
        this.user = user;
        this.store = store;
        this.pickUpDateTime = LocalDateTime.parse(dressReservationDto.getPickUpDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.comment = dressReservationDto.getComment();
        this.price = dressReservationDto.getPrice();
        this.reservedDressList = reservedDressList;
        this.status = DressReservationStatus.Constants.orderCompletion;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
