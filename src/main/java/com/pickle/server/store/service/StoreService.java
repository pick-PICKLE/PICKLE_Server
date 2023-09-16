package com.pickle.server.store.service;

import com.pickle.server.common.error.CustomException;
import com.pickle.server.dress.domain.DressCategory;
import com.pickle.server.dress.dto.DressBriefInStoreDto;
import com.pickle.server.store.domain.Store;
import com.pickle.server.store.domain.StoreLike;
import com.pickle.server.store.dto.StoreCoordDto;
import com.pickle.server.store.dto.StoreDetailDto;
import com.pickle.server.store.dto.StoreLikeDto;
import com.pickle.server.store.dto.UpdateStoreLikeDto;
import com.pickle.server.store.repository.StoreLikeRepository;
import com.pickle.server.store.repository.StoreRepository;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pickle.server.common.error.ErrorResponseStatus.*;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final StoreLikeRepository storeLikeRepository;


    public List<StoreCoordDto> getNearStores(Long userId, Double lat, Double lng) {
        List<StoreCoordDto> nearStores = storeRepository.findNearStore(userId, lat, lng);

        return nearStores;
    }


    public StoreDetailDto findStoreDetailInfoByStoreId(Long storeId, String category, User user){
        if(!DressCategory.existsCategoryByName(category))
            throw new CustomException(BAD_REQUEST_INVALID_SEARCH_PARAMETER);

        Store store = storeRepository.findById(storeId).orElseThrow(
                ()-> new CustomException(NOT_FOUND_STORE_ID)
        );

        List<DressBriefInStoreDto> dressBriefInStoreDtoList
                = storeRepository.findDressDtoByStoreIdAndCategory(storeId,category, user.getId());
        return new StoreDetailDto(store, dressBriefInStoreDtoList, storeLikeRepository.existsByUserIdAndStoreId(user.getId(), storeId));
    }

    @Transactional(readOnly = true)
    public List<StoreLikeDto> findStoreLikeByUser(User user){
        return storeRepository.findStoreByUsers(user.getId());
    }

    @Transactional
    public UpdateStoreLikeDto likesStore(UpdateStoreLikeDto updateStoreLikeDto, User user){
        Store store = storeRepository.findById(updateStoreLikeDto.getStoreId()).orElseThrow(()-> new CustomException(NOT_FOUND_STORE_ID));
        if(storeLikeRepository.findByUserAndStore(user,store).isPresent()){
            storeLikeRepository.deleteStoreLikeByStoreIdAndUserId(store.getId(),user.getId());

            return new UpdateStoreLikeDto(updateStoreLikeDto.getStoreId(), Boolean.FALSE);
        } else{
            StoreLike storeLike = StoreLike.builder().store(store).user(user).build();
            storeLikeRepository.save(storeLike);

            return new UpdateStoreLikeDto(updateStoreLikeDto.getStoreId(), Boolean.TRUE);
        }
    }
}
