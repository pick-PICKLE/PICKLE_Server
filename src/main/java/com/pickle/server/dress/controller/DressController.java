package com.pickle.server.dress.controller;

import com.pickle.server.dress.dto.DressLikeDto;
import com.pickle.server.dress.dto.UpdateDressLikeDto;
import com.pickle.server.dress.service.DressService;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/dresses")
public class DressController {
    @Autowired
    private final DressService dressService;
    private final UserRepository userRepository;

    @GetMapping("/likes/{id}")
    public Page<DressLikeDto> findDressLikeByUser(@PathVariable("id") Long userId, @PageableDefault(size=10) Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        return dressService.findDressLikeByUser(userId,pageable);
    }

    @PostMapping("/likes")
    public ResponseEntity<UpdateDressLikeDto> likeDress(@RequestBody UpdateDressLikeDto updatedressLikeDto){
        dressService.likesDress(updatedressLikeDto);
        return new ResponseEntity<>(updatedressLikeDto
                , HttpStatus.valueOf(200));
    }

    @PostMapping("/likes/delete")
    public void delLikeDress(@RequestBody UpdateDressLikeDto updatedressLikeDto){
        dressService.delLikeDress(updatedressLikeDto);

    }
}
