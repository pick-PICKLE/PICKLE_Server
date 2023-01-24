package com.pickle.server.likes.domain;

import com.pickle.server.dress.domain.Dress;
import com.pickle.server.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DressLike {

    @Id
    //  @Column(columnDefinition = "integer default 0")
    //    @Column(name="dresslike_id",columnDefinition = "0")
    @Column(name ="dresslike_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dress_id", nullable = false)
    private Dress dress;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
