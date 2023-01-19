package com.pickle.server.dress.repository;

import com.pickle.server.dress.domain.Dress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DressRepository extends JpaRepository<Dress, Long> {
    //@Query(value = "select * from Dress where (id > :id) and (createdAt >= DATE_SUB(now(),INTERVAL 7 DAY))", nativeQuery = true)

    /**
     최근 업로드 된 상품 확인
     */
    @Query(value = "select d from Dress d where d.store.id = :id and d.createdAt >= :stdDate")
    List<Dress> findAllByCreatedAtAndStore(@Param(value = "id") Long i, @Param(value="stdDate") LocalDateTime stdDate);

    /**
     선택된 카테고리에서 매장별 최근 5개씩
     */
    @Query(value = "select * from Dress where Dress.store_id = :id and Dress.category = :category order by createdAt DESC LIMIT 5", nativeQuery = true)
    List<Dress> findRandomCategory(@Param(value = "id") Long i, @Param(value="category") String category);
}