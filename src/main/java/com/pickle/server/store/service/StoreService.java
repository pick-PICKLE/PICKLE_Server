package com.pickle.server.store.service;

import com.pickle.server.store.domain.Store;
import com.pickle.server.store.domain.StoreLike;
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

@Service
@RequiredArgsConstructor
public class StoreService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final StoreLikeRepository storeLikeRepository;

    @Transactional(readOnly = true)
    public Page<StoreLikeDto> findStoreLikeByUser(Long userId, Pageable pagerequest){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        return storeLikeRepository.findStoreByUsers(userId, pagerequest);
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
