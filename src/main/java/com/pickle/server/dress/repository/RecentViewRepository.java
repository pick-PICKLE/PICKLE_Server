package com.pickle.server.dress.repository;

import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.domain.RecentView;
import com.pickle.server.dress.dto.DressDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface RecentViewRepository extends JpaRepository<RecentView, Long> {
    @Query(value="select new com.pickle.server.dress.dto.DressDto(R.dress.id, R.dress.name, R.dress.price, R.dress.image, R.dress.store.name) " +
            "from RecentView R where R.user.id = :id and R.modifiedAt >= :stdDate order by R.modifiedAt Desc")
    List<DressDto> findByUserId(@Param(value = "id") Long i, @Param(value="stdDate")LocalDateTime stdDate);
}
