
package com.pickle.server.user.domain;



import com.pickle.server.common.Timestamped;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;

@Getter
@Entity
public class User extends Timestamped {

    @Id
    @Column(name ="user_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String image;

    @Column
    private String origin; //무슨 로그인?

//    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
//    private List<Reserve> reserves = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
//    private List<DressLike> dressLikes = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
//    private List<StoreLike> storeLikes = new ArrayList<>();



}
