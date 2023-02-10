package com.pickle.server.store.repository;

import com.pickle.server.store.domain.Store;
import com.pickle.server.store.domain.StoreLike;
import com.pickle.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreLikeRepository extends JpaRepository<StoreLike,Long> {
    Optional<StoreLike> findByUserAndStore(User user, Store store);

    @Modifying
    @Query(value = "delete from Store_Like where store_id= :store_id and user_id= :user_id",nativeQuery = true)
    void deleteStore(@Param("store_id") Store store, @Param("user_id") User user);

    Boolean existsByUserIdAndStoreId(Long userId, Long storeId);

}
