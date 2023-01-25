package com.pickle.server.likes.domain;

import com.pickle.server.store.domain.Store;
import com.pickle.server.user.domain.User;

import javax.persistence.*;

public class StoreLike {
    @Id
    @Column(name ="storelike_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
