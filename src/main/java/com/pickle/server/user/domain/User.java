
package com.pickle.server.user.domain;



import com.pickle.server.common.Timestamped;
import com.pickle.server.user.dto.UserDto;
import com.pickle.server.user.dto.UserUpdateDto;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends Timestamped  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void updateProfile(UserUpdateDto userUpdateDto){
        this.name = userUpdateDto.getName();
        this.email = userUpdateDto.getEmail();
        this.image = userUpdateDto.getImage();
    }

}
