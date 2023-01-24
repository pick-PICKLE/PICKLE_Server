package com.pickle.server.store.service;

import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.dto.DressBriefDto;
import com.pickle.server.store.domain.Store;
import com.pickle.server.store.dto.StoreDetailDto;
import com.pickle.server.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final KeyValueService keyValueService;

    public StoreDetailDto findStoreDetailInfoByStoreId(Long storeId, String category){
        Store store = storeRepository.findById(storeId).orElseThrow(
                ()->new RuntimeException("해당 id의 스토어를 찾을 수 없습니다.")
        );
        List<DressBriefDto> dressBriefDtoList;
        if(category == null){
            dressBriefDtoList = storeRepository.findDressDtoByStoreId(storeId);
        }
        else{
            dressBriefDtoList = storeRepository.findDressDtoByStoreIdAndCategory(storeId,category);
        }

        return new StoreDetailDto(store, dressBriefDtoList, keyValueService.makeUrlHead("stores"));
    }
}
