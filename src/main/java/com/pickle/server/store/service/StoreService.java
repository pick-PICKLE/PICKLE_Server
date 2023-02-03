package com.pickle.server.store.service;

import com.pickle.server.common.util.KeyValueService;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final KeyValueService keyValueService;
    private final UserRepository userRepository;
    private final StoreLikeRepository storeLikeRepository;


    public List<StoreCoordDto> getNearStores(Double lat, Double lng) {
        List<Store> allStores = storeRepository.findAll();
        List<StoreCoordDto> nearStores = new ArrayList<>();

        for (int i = 0; i < allStores.size(); i++) {
            Double sLat = allStores.get(i).getLatitude();
            Double SLng = allStores.get(i).getLongitude();

            // 매장과 사용자 현재 위치 사이의 거리 (meter)
            Double dist = distance(lat, lng, sLat, SLng);

            // 1km 내에 있는 매장
            if (dist <= 1000.0) {
                StoreCoordDto storeCoordDto = new StoreCoordDto(allStores.get(i), dist);
                nearStores.add(storeCoordDto);
            }
        }

        // 매장 거리순으로 정렬
        Collections.sort(nearStores, new Comparator<StoreCoordDto>() {
            @Override
            public int compare(StoreCoordDto s1, StoreCoordDto s2) {
                if(s1.getDist()-s2.getDist() < 0) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        return nearStores;
    }

    // 거리 미터 단위로 계산
    private static Double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = (dist * 180 / Math.PI);
        dist = dist * 60 * 1.1515 * 1609.344;

        return (dist);
    }

    private static Double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public StoreDetailDto findStoreDetailInfoByStoreId(Long storeId, String category, User user){
        if(!DressCategory.findCategoryByName(category))
            throw new IllegalArgumentException("잘못된 카테고리 입니다.");

        Store store = storeRepository.findById(storeId).orElseThrow(
                ()->new RuntimeException("해당 id의 스토어를 찾을 수 없습니다.")
        );

        List<DressBriefInStoreDto> dressBriefInStoreDtoList
                = storeRepository.findDressDtoByStoreIdAndCategory(storeId,category);


        return new StoreDetailDto(store, dressBriefInStoreDtoList,
                keyValueService.makeUrlHead("stores"),
                storeLikeRepository.existsByUserIdAndStoreId(user.getId(), storeId));
    }

    @Transactional(readOnly = true)
    public List<StoreLikeDto> findStoreLikeByUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        return storeRepository.findStoreByUsers(userId);
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
