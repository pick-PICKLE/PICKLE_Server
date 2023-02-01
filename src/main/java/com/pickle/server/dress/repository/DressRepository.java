package com.pickle.server.dress.repository;

import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.dto.DressOverviewDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface DressRepository extends JpaRepository<Dress, Long>, DressDslRepository, QuerydslPredicateExecutor<Dress> {
    /*
    최근 업로드 된 상품 확인
     */
    @Query(value = "select d from Dress d where d.createdAt >= :stdDate order by d.createdAt desc")
    List<Dress> findAllByCreatedAt(@Param(value="stdDate") LocalDateTime stdDate);

    @Query(value = "select d from Dress d where d.createdAt >= :stdDate and d.store.id =:id order by d.createdAt")
    List<Dress> findAllByCreatedAtAndStore(@Param(value="id") Long id, @Param(value="stdDate") LocalDateTime stdDate);

    /**
     선택된 카테고리에서 매장별 최근 5개씩
     */
    @Query(value = "select d from Dress d where d.store.id = :id and d.category = :category order by d.createdAt DESC ")
    List<Dress> findRandomCategory(@Param(value = "id") Long id, @Param(value="category") String category);

    //List<Dress> findTop5ByStoreIdAndCategory(Long storeId, String category);
}