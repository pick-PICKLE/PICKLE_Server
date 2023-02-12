package com.pickle.server.store.repository;

import com.pickle.server.dress.dto.DressBriefInStoreDto;
import com.pickle.server.store.dto.StoreCoordDto;
import com.pickle.server.store.dto.StoreLikeDto;


import java.util.List;

public interface StoreDslRepository {

    List<DressBriefInStoreDto> findDressDtoByStoreIdAndCategory(Long storeId, String category, Long userId);
    List<StoreLikeDto> findStoreByUsers(Long userId);
    List<StoreCoordDto> findNearStore(Long userId, Double latitude, Double longitude);

}
