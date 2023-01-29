package com.pickle.server.store.service;

import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.domain.DressCategory;
import com.pickle.server.dress.dto.DressBriefDto;
import com.pickle.server.store.domain.Store;
import com.pickle.server.store.domain.StoreLike;
import com.pickle.server.store.dto.StoreDetailDto;
import com.pickle.server.store.dto.StoreLikeDto;
import com.pickle.server.store.dto.UpdateStoreLikeDto;
import com.pickle.server.store.repository.StoreLikeRepository;
import com.pickle.server.store.repository.StoreRepository;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final KeyValueService keyValueService;
    private final UserRepository userRepository;
    private final StoreLikeRepository storeLikeRepository;

    public StoreDetailDto findStoreDetailInfoByStoreId(Long storeId, String category){
        if(category != null && !DressCategory.findCategoryByName(category))
            throw new IllegalArgumentException("잘못된 카테고리 입니다.");

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
    @Transactional(readOnly = true)
    public List<StoreLikeDto> findStoreLikeByUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        return storeLikeRepository.findStoreByUsers(userId);
    }
    @Transactional
    public void likesStore(UpdateStoreLikeDto updateStoreLikeDto){
        User user = userRepository.findById(updateStoreLikeDto.getUserId()).orElseThrow(()->new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        Store store = storeRepository.findById(updateStoreLikeDto.getStoreId()).orElseThrow(()-> new RuntimeException("해당 id의 스토어를 찾을 수 없습니다."));
        if(storeLikeRepository.findByUserAndStore(user,store).isPresent()){throw new RuntimeException();}
        else{
            StoreLike storeLike = StoreLike.builder().store(store).user(user).build();
            storeLikeRepository.save(storeLike);
        }
    }
    @Transactional
    public void delLikeStore(UpdateStoreLikeDto updateStoreLikeDto){
        User user = userRepository.findById(updateStoreLikeDto.getUserId()).orElseThrow(()->new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        Store store = storeRepository.findById(updateStoreLikeDto.getStoreId()).orElseThrow(()->new RuntimeException("해당 id의 스토어를 찾을 수 없습니다."));
        if(storeLikeRepository.findByUserAndStore(user,store).isPresent()){storeLikeRepository.deleteStore(store,user);}
        else{throw new RuntimeException();}
    }
}
