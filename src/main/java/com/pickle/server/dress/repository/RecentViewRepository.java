package com.pickle.server.dress.repository;

import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.domain.RecentView;
import com.pickle.server.dress.dto.DressOverviewDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecentViewRepository extends JpaRepository<RecentView, Long> {
    @Query(value="select R from RecentView R where R.user.id =:userId and R.dress.id =:dressId")
    Optional<RecentView> findByUserIdAndStoreId(@Param(value="userId") Long userId, @Param(value="dressId") Long dressId);
}
