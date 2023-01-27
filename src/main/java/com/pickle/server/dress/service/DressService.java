package com.pickle.server.dress.service;

import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.domain.DressLike;
import com.pickle.server.dress.dto.DressLikeDto;
import com.pickle.server.dress.dto.UpdateDressLikeDto;
import com.pickle.server.dress.repository.DressLikeRepository;
import com.pickle.server.dress.repository.DressRepository;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DressService {
    private final UserRepository userRepository;
    private final DressLikeRepository dressLikeRepository;
    private final DressRepository dressRepository;

    @Transactional(readOnly = true)
    public Page<DressLikeDto> findDressLikeByUser(Long userId, Pageable pagerequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        return dressLikeRepository.findDressByUsers(userId, pagerequest);
    }
    @Transactional
    public void likesDress(UpdateDressLikeDto updateDressLikeDto){
        User user = userRepository.findById(updateDressLikeDto.getUserId()).orElseThrow(()->new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        Dress dress = dressRepository.findById(updateDressLikeDto.getDressId()).orElseThrow(()->new RuntimeException("해당 id의 게시글을 찾을 수 없습니다."));
        if(dressLikeRepository.findByUserAndDress(user,dress).isPresent()){ throw new RuntimeException(); }
        else{
            DressLike dressLike = DressLike.builder().dress(dress).user(user).build();
            dressLikeRepository.save(dressLike);
        }
    }
    @Transactional
    public void delLikeDress(UpdateDressLikeDto updateDressLikeDto){
        User user = userRepository.findById(updateDressLikeDto.getUserId()).orElseThrow(()->new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        Dress dress = dressRepository.findById(updateDressLikeDto.getDressId()).orElseThrow(()->new RuntimeException("해당 id의 게시글을 찾을 수 없습니다."));
        if(dressLikeRepository.findByUserAndDress(user,dress).isPresent()){
            dressLikeRepository.deleteDress(dress, user);
        }
        else{ throw new RuntimeException(); }
    }

}
