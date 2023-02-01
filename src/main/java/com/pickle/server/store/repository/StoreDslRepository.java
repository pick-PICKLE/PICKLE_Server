package com.pickle.server.store.repository;

import com.pickle.server.dress.dto.DressBriefDto;
import com.pickle.server.store.dto.StoreLikeDto;

import java.util.List;

public interface StoreDslRepository {
    List<DressBriefDto> findDressDtoByStoreId(Long storeId);
    List<DressBriefDto> findDressDtoByStoreIdAndCategory(Long storeId, String category);

    List<StoreLikeDto> findStoreByUsers(Long userId);
}
