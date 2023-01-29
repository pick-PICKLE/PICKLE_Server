package com.pickle.server.store.repository;

import com.pickle.server.store.domain.StoreLike;
import com.pickle.server.store.dto.StoreLikeDto;
import com.pickle.server.store.domain.Store;
import com.pickle.server.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreLikeRepository extends JpaRepository<StoreLike,Long> {
    Optional<StoreLike> findByUserAndStore(User user, Store store);

    @Modifying
    @Query("delete from StoreLike where store_id= :store_id and user_id= :user_id")
    void deleteStore(@Param("store_id") Store store, @Param("user_id") User user);
 //   @Query("select s from StoreLike s where s.id= :id")
   // Page<StoreLikeDto> findStoreByUsers(@Param("id") Long userId, Pageable pageable);
 @Query("select new com.pickle.server.store.dto.StoreLikeDto(s.store) from StoreLike s where s.id= :id")
 List<StoreLikeDto> findStoreByUsers(@Param("id") Long userId);

}
