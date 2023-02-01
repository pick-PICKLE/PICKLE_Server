package com.pickle.server.dress.repository;

import com.pickle.server.dress.dto.DressLikeDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DressDslRepository {
    List<DressLikeDto> findDressByUsers(@Param("id") Long userId);
}
