package com.pickle.server.dress.repository;

import com.pickle.server.dress.dto.DressBriefDto;
import com.pickle.server.dress.dto.DressLikeDto;
import com.pickle.server.dress.dto.DressOverviewDto;
import org.springframework.data.repository.query.Param;
import com.pickle.server.dress.dto.DressReservationFormDto;

import java.time.LocalDateTime;
import java.util.List;

public interface DressDslRepository {

    List<DressBriefDto> findDressByCondition(String name, String sort, String category, Double latitude, Double longitude);

    List<DressLikeDto> findDressByUsers(@Param("id") Long userId);

    List<DressOverviewDto> findDressByRecentView(Long userId, LocalDateTime stdDate);

    List<DressOverviewDto> findDressByStoreAndCreatedAt(Long userId, Double latitude, Double longitude, LocalDateTime stdDate);

    List<DressOverviewDto> findDressByCategory(Long userId, String category, Double latitude, Double longitude);
}
