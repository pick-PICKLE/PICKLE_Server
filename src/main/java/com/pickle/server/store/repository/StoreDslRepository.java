package com.pickle.server.store.repository;

import com.pickle.server.dress.dto.DressBriefInStoreDto;

import java.util.List;

public interface StoreDslRepository {
    List<DressBriefInStoreDto> findDressDtoByStoreIdAndCategory(Long storeId, String category);
}
