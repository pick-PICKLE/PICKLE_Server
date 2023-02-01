package com.pickle.server.dress.repository;

import com.pickle.server.dress.dto.DressBriefDto;
import com.pickle.server.dress.dto.DressReservationFormDto;

import java.util.List;

public interface DressDslRepository {
    List<DressBriefDto> findDressByCondition(String name, String sort, String category, Double latitude, Double longitude);
}
