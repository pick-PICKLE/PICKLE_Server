package com.pickle.server.dress.service;

import com.pickle.server.common.error.NotFoundIdException;
import com.pickle.server.common.error.NotValidParamsException;
import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.domain.*;
import com.pickle.server.dress.dto.DressBriefDto;
import com.pickle.server.dress.dto.DressDetailDto;
import com.pickle.server.dress.dto.DressLikeDto;
import com.pickle.server.dress.dto.UpdateDressLikeDto;
import com.pickle.server.dress.repository.DressLikeRepository;
import com.pickle.server.dress.repository.DressRepository;
import com.pickle.server.dress.repository.RecentViewRepository;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DressService {
    private final DressRepository dressRepository;
    private final UserRepository userRepository;
    private final DressLikeRepository dressLikeRepository;
    private final KeyValueService keyValueService;
    private final RecentViewRepository recentViewRepository;

    public DressDetailDto findDressDetailInfoByDressId(Long dressId, User user) {
        Dress dress = dressRepository.findById(dressId).orElseThrow(
                () -> new NotFoundIdException()
        );

        Optional<RecentView> recentView = recentViewRepository.findByUserIdAndStoreId(user.getId(), dress.getId());
        if (recentView.isPresent()) {
            recentView.get().setClick(recentView.get().getClick() + 1);
            recentViewRepository.save(recentView.get());
        } else {
            recentViewRepository.save(new RecentView(dress, user));
        }

        return new DressDetailDto(dress, keyValueService.makeUrlHead("dresses"));
    }

    public List<DressBriefDto> searchDress(String name, String sort, String category, Double latitude, Double longitude) {
        if(!DressCategory.findCategoryByName(category))
            throw new NotValidParamsException();

        if(!DressSortBy.findSortConditionByName(sort))
            throw new NotValidParamsException();

        return dressRepository.findDressByCondition(name, sort, category, latitude, longitude);
    }

    @Transactional(readOnly = true)
    public List<DressLikeDto> findDressLikeByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        return dressRepository.findDressByUsers(userId);
    }
    @Transactional
    public void likesDress(UpdateDressLikeDto updateDressLikeDto){
        User user = userRepository.findById(updateDressLikeDto.getUserId()).orElseThrow(()->new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        Dress dress = dressRepository.findById(updateDressLikeDto.getDressId()).orElseThrow(()->new NotFoundIdException());
//        if(dressLikeRepository.findByUserAndDress(user,dress).isPresent()){ throw new RuntimeException(); }
        if(dressLikeRepository.findByUserAndDress(user,dress).isPresent()){
            dressLikeRepository.deleteDress(dress, user); //api 하나로 사용
        }

        else{
            DressLike dressLike = DressLike.builder().dress(dress).user(user).build();
            dressLikeRepository.save(dressLike);
        }
    }
/*    @Transactional
    public void delLikeDress(UpdateDressLikeDto updateDressLikeDto){
        User user = userRepository.findById(updateDressLikeDto.getUserId()).orElseThrow(()->new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        Dress dress = dressRepository.findById(updateDressLikeDto.getDressId()).orElseThrow(()->new RuntimeException("해당 id의 게시글을 찾을 수 없습니다."));
        if(dressLikeRepository.findByUserAndDress(user,dress).isPresent()){
            dressLikeRepository.deleteDress(dress, user);
        }
        else{ throw new RuntimeException(); }
    }*/

}
