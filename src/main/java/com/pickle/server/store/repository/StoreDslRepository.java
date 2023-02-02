package com.pickle.server.store.repository;

import com.pickle.server.dress.dto.DressBriefInStoreDto;
import com.pickle.server.dress.dto.DressBriefDto;
import com.pickle.server.store.dto.StoreLikeDto;


import java.util.List;

public interface StoreDslRepository {

    List<DressBriefInStoreDto> findDressDtoByStoreIdAndCategory(Long storeId, String category);
    List<StoreLikeDto> findStoreByUsers(Long userId);

}
