package com.pickle.server.likes.domain;

import com.pickle.server.dress.domain.Dress;
import com.pickle.server.store.domain.Store;
import com.pickle.server.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
//좋아요 누르기
@DynamicInsert
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Likes {
    @Id
    @Column(name ="like_id")
 //   @Column(name="like_id",columnDefinition = "0")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //=0
/*
    @Column(name="like_count")
    @ColumnDefault("0")
    private Integer count;*/

    @ManyToOne
    @JoinColumn(name = "dress_id", nullable = false)
    private Dress dress;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="store_id",nullable = false)
    private Store store;

  //  @OneToMany(mappedBy = "like")
   // private List<Dress> dresses = new ArrayList<>();
}
