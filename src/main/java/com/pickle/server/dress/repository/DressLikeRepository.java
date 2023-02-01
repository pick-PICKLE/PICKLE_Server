package com.pickle.server.dress.repository;

import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.domain.DressLike;
import com.pickle.server.dress.dto.DressLikeDto;
import com.pickle.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DressLikeRepository extends JpaRepository<DressLike,Long> {
    //좋아요 누른 적 있는지 체크
    Optional<DressLike> findByUserAndDress(User user, Dress dress);
    //좋아요 취소
    @Modifying
    @Query(value = "delete from Dress_Like where dress_id= :dress_id and user_id= :user_id",nativeQuery = true)
    void deleteDress(@Param("dress_id") Dress dress, @Param("user_id") User user);
//    @Query("delete from DressLike where dress_id= :dress_id and user_id= :user_id")
//    void deleteDress(@Param("dress_id") Dress dress, @Param("user_id") User user);

    //좋아요 옷 목록 조회
   //@Query("select new com.pickle.server.dress.dto.DressLikeDto(d.dress) from DressLike d where d.id= :id")
//    @Query("select d from DressLike d where d.id= :id")


}
