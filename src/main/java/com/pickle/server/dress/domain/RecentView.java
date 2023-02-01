package com.pickle.server.dress.domain;

import com.pickle.server.common.Timestamped;
import com.pickle.server.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "recent_view")
public class RecentView extends Timestamped {
    @Id
    @Column(name ="recent_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int click;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "dress_id", nullable = false)
    private Dress dress;

    public RecentView(Dress dress, User user) {
        this.click = 1;
        this.user = user;
        this.dress = dress;
    }
}
