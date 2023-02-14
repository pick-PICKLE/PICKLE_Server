package com.pickle.server.dress.repository;

import com.pickle.server.dress.dto.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DressDslRepository {

    List<DressBriefDto> findDressByCondition(String name, String sort, String category, Double latitude, Double longitude, Long userId);

    List<DressLikeDto> findDressByUsers(@Param("id") Long userId);

    List<DressOverviewDto> findDressByRecentView(Long userId, LocalDateTime stdDate);

    List<DressOverviewDto> findDressByStoreAndCreatedAt(Long userId, Double latitude, Double longitude, LocalDateTime stdDate);

    List<DressOverviewDto> findDressByCategory(Long userId, String category, Double latitude, Double longitude);
    List<DressOrderDto> findReservationByUserAndReservationId(Long dressReservationId,Long userId);

    List<DressOrderListDto> findReservationListByStatusAndUser(String status, Long userId);
}
