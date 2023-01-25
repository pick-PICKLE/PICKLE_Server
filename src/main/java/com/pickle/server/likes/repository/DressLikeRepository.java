package com.pickle.server.likes.repository;

import com.pickle.server.likes.domain.DressLike;
import com.pickle.server.likes.domain.Likes;
import com.pickle.server.likes.dto.DressLikeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface DressLikeRepository extends JpaRepository<DressLike,Long> {

  //  @Query(value="select * from dress_like where user_id= :id",nativeQuery = true)
  @Query("select new com.pickle.server.likes.dto.DressLikeDto(d.dress) from DressLike d where d.id= :id")
  Page<DressLikeDto> findDressByUsers(@Param("id") Long userId, Pageable pageable);
}
