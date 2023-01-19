package com.pickle.server.dress.repository;

import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.domain.RecentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface RecentViewRepository extends JpaRepository<RecentView, Long> {
    @Query(value="select dress from RecentView where RecentView.user.id = :id and RecentView.modifiedAt >= :stdDate order by modifiedAt Desc", nativeQuery = true)
    List<Dress> findByUserId(@Param(value = "id") Long i, @Param(value="stdDate")LocalDateTime stdDate);
}
