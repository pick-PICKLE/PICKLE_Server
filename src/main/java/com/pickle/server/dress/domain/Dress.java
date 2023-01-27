
package com.pickle.server.dress.domain;


import com.pickle.server.common.Timestamped;
import com.pickle.server.likes.domain.Likes;
import com.pickle.server.store.domain.Store;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Dress extends Timestamped {

    @Id
    @Column(name ="dress_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String category;

    @Column
    private String name;

    @Column
    private Integer price;
    //
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column
    private String image;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "dress")
    private List<DressOption> dressOptions = new ArrayList<>();


    @OneToMany(mappedBy = "dress")
    private List<Likes> likes = new ArrayList<>();
    //지우기
    @OneToMany(mappedBy = "dress")
    private List<DressLike> dresslike = new ArrayList<>();

}
