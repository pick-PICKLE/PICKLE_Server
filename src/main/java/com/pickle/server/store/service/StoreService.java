package com.pickle.server.store.service;

import com.pickle.server.common.error.NotFoundIdException;
import com.pickle.server.common.error.NotValidParamsException;
import com.pickle.server.dress.domain.DressCategory;
import com.pickle.server.dress.dto.DressBriefInStoreDto;
import com.pickle.server.dress.dto.UpdateDressLikeDto;
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

    private static Double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public StoreDetailDto findStoreDetailInfoByStoreId(Long storeId, String category, User user){
        if(!DressCategory.existsCategoryByName(category))
            throw new NotValidParamsException();

        Store store = storeRepository.findById(storeId).orElseThrow(
                ()->new NotFoundIdException()
        );

        List<DressBriefInStoreDto> dressBriefInStoreDtoList
                = storeRepository.findDressDtoByStoreIdAndCategory(storeId,category, user.getId());
        return new StoreDetailDto(store, dressBriefInStoreDtoList, storeLikeRepository.existsByUserIdAndStoreId(user.getId(), storeId));
    }

    @Transactional(readOnly = true)
    public List<StoreLikeDto> findStoreLikeByUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        return storeRepository.findStoreByUsers(userId);
    }
    @Transactional
    public UpdateStoreLikeDto likesStore(UpdateStoreLikeDto updateStoreLikeDto, User user){
        Store store = storeRepository.findById(updateStoreLikeDto.getStoreId()).orElseThrow(()-> new NotFoundIdException());
        if(storeLikeRepository.findByUserAndStore(user,store).isPresent()){
            storeLikeRepository.deleteStore(store.getId(),user.getId());

            return new UpdateStoreLikeDto(updateStoreLikeDto.getStoreId(), Boolean.FALSE);
        } else{
            StoreLike storeLike = StoreLike.builder().store(store).user(user).build();
            storeLikeRepository.save(storeLike);

            return new UpdateStoreLikeDto(updateStoreLikeDto.getStoreId(), Boolean.TRUE);
        }
    }
    /*
    @Transactional
    public void delLikeStore(UpdateStoreLikeDto updateStoreLikeDto){
        User user = userRepository.findById(updateStoreLikeDto.getUserId()).orElseThrow(()->new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        Store store = storeRepository.findById(updateStoreLikeDto.getStoreId()).orElseThrow(()->new RuntimeException("해당 id의 스토어를 찾을 수 없습니다."));
        if(storeLikeRepository.findByUserAndStore(user,store).isPresent()){storeLikeRepository.deleteStore(store,user);}
        else{throw new RuntimeException();}
    }*/
}
