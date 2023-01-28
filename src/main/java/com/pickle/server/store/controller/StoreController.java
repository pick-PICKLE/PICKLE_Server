package com.pickle.server.store.controller;

import com.pickle.server.store.dto.StoreLikeDto;
import com.pickle.server.store.dto.UpdateStoreLikeDto;
import com.pickle.server.store.service.StoreService;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.repository.UserRepository;
import io.swagger.models.Response;
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
@RequestMapping("/stores")
public class StoreController {
    @Autowired
    UserRepository userRepository;
    private final StoreService storeService;

    @PostMapping("/likes")
    public ResponseEntity<UpdateStoreLikeDto> likeStore(@RequestBody UpdateStoreLikeDto updatestoreLikeDto){
        storeService.likesStore(updatestoreLikeDto);
        return new ResponseEntity<UpdateStoreLikeDto>(updatestoreLikeDto
                , HttpStatus.valueOf(200));
    }
    @GetMapping("/likes/{id}")
    public Page<StoreLikeDto> findStoreLikeByUser(@PathVariable("id") Long userId, @PageableDefault(size=10) Pageable pageable){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        return storeService.findStoreLikeByUser(userId,pageable);
    }

    @PostMapping("/likes/delete")
    public ResponseEntity<UpdateStoreLikeDto> delLikeStore(@RequestBody UpdateStoreLikeDto updateStoreLikeDto){
        storeService.delLikeStore(updateStoreLikeDto);
        return new ResponseEntity<UpdateStoreLikeDto>(updateStoreLikeDto,
                HttpStatus.valueOf(200));
    }
 /*    @PostMapping("/likes/delete")
    public void delLikeStore(@RequestBody UpdateStoreLikeDto updateStoreLikeDto){
        storeService.delLikeStore(updateStoreLikeDto);

    }*/
}
