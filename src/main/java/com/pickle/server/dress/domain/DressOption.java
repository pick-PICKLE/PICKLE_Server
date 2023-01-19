
package com.pickle.server.dress.domain;

import com.pickle.server.common.Timestamped;
import com.pickle.server.store.domain.Store;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "dress_option")
public class DressOption {

    @Id
    @Column(name ="option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "dress_id", nullable = false)
    private Dress dress;

    @Column(name = "is_in_stock")
    private Boolean isInStock;

}
