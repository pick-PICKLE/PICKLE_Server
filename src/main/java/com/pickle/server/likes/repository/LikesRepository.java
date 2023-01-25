package com.pickle.server.likes.repository;

import com.pickle.server.likes.domain.DressLike;
import com.pickle.server.likes.domain.Likes;

import com.pickle.server.likes.dto.DressLikeDto;
import com.pickle.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes,Long> {
    //좋아요에 사용
    Optional<Likes> findById(Long dress_id);
 //   List<Likes> findAll();


    //test용
    @Query(value="select * from likes l where l.like_id=1",nativeQuery=true)
    Page<Likes> findAllDesc(Pageable pageable);
    //옷 좋아요 누르기
    @Query(value="select * from dress where user_id= :userid",nativeQuery = true)
    List<DressLikeDto> findDressByUser(Optional<User> user);

    //의상 좋아요 리스트


    //스토어 좋아요 리스트
    @Query(value="select * from store_like where user_id= :id",nativeQuery = true)
    Page<DressLike> findStoreByUser(Long storelike_id,Pageable pageable);

    //옷 좋아요 누르기
  /*  @Modifying 에러 문제-user가 null임
    @Query(value = "insert into likes(dress_id,user_id) values(:like_id,:user_id)",nativeQuery = true)
    void dressCart(@Param("dress_id")Long dress_id, @Param("user_id")Long user_id);
*/
    //옷 좋아요 누르기
    @Modifying
    @Query(value = "insert into dress_like(dress_id) values(:dress_id)", nativeQuery = true)
    void updateDressLikes(@Param("dress_id") Long dress_id);

    //옷 좋아요 취소
    @Modifying
    @Query(value = "delete from dress_like where dress_id = :dress_id",nativeQuery = true)
    void delDressLikes(@Param("dress_id")Long dress_id);

    //좋아요 누른 적 있는지 체크
    @Query(value = "SELECT COUNT(dresslike_id) FROM dress_like WHERE dress_id=:dress_id",nativeQuery = true)
//    Long findDressLikeByUser(@Param("dress_id") Long dress_id);
    Long findDressLike(@Param("dress_id") Long dress_id);

}
