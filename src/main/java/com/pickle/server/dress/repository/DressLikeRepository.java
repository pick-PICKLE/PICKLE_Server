package com.pickle.server.dress.repository;

import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.domain.DressLike;
import com.pickle.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DressLikeRepository extends JpaRepository<DressLike,Long> {
    Optional<DressLike> findByUserAndDress(User user, Dress dress);

    /*@Modifying
    @Query(value = "delete from dress_like where dress_id= :dress_id and user_id= :user_id",nativeQuery = true)
    void deleteDress(@Param("dress_id") Dress dress, @Param("user_id") User user);*/

    @Modifying
    @Query(value = "delete from dress_like where dress_id= :dress_id and user_id= :user_id",nativeQuery = true)
    void deleteDress(@Param("dress_id") Long dress, @Param("user_id") Long user);

    Boolean existsByUserIdAndDressId(Long id, Long dressId);
}
