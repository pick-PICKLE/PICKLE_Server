
package com.pickle.server.user.domain;



import com.pickle.server.common.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends Timestamped  {

    @Id
    @GeneratedValue
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

    @Builder
    public User(String name, String email, String image, String origin) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.origin = origin;
    }
    //    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
//    private List<Reserve> reserves = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
//    private List<DressLike> dressLikes = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
//    private List<StoreLike> storeLikes = new ArrayList<>();



}
