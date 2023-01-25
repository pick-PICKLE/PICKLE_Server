package com.pickle.server.likes.controller;

import com.pickle.server.common.util.response.BaseApiResponse;
import com.pickle.server.likes.domain.DressLike;
import com.pickle.server.likes.domain.Likes;
import com.pickle.server.likes.dto.DressLikeDto;

import com.pickle.server.likes.dto.LikesDto;
import com.pickle.server.likes.dto.StoreLikeDto;
import com.pickle.server.likes.repository.DressLikeRepository;
import com.pickle.server.likes.repository.LikesRepository;
import com.pickle.server.likes.service.LikesService;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.dto.UserDto;
import com.pickle.server.user.repository.UserRepository;

import com.pickle.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping("/likes")
public class LikesController {
    @Autowired
    UserRepository userRepository;
    private final LikesService likesService;
    private final UserService userService;

    //  의상 좋아요 누르기
/*    @PostMapping("/dresses/likes/{dress_id}")
    public void likeDress(@PathVariable Long dress_id){
        likesService.likesDress(dress_id);
    }*/
 /*   @GetMapping("/dresses/like")
    public List<DressLikeDto> findDressByUser(User user){
        return likesService.findDressByUser(user);
    }*/
    //옷 좋아요 목록 조회
    @GetMapping("/dresses/likes/{id}")
    public Page<DressLikeDto> findDressLikeByUser(@PathVariable("id") Long userId, @PageableDefault(size=5) Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        return likesService.findDressLikeByUser(userId,pageable);
    }

    /*
    @GetMapping("/dresses/like")
    public Page<DressLikeDto> findDressLikeByUser(String email, @PageableDefault(size=5) Pageable pageable){
        Long dresslikeId = userRepository.findByEmail(email).getId();
  //      Optional<User> dresslikeId = userRepository.findById(userDto.getUser_id());
        return likesService.findDressLikeByUser(dresslikeId,pageable);
    }*/

    //스토어 좋아요 목록 조회
  /*  @GetMapping("/stores/like")
    public Page<StoreLikeDto> findStoreLikeByUser(String email, @PageableDefault(size=5) Pageable pageable){
        Long storelikeId = userRepository.findByEmail(email).getUser_id();
        log.info("이메일"); //어디서 문제인지 확인용
        return likesService.findStoreLikeByUser(storelikeId,pageable);
    }*/

    /*baseapiresponse 사용
   @GetMapping("/stores/like")
   public List<StoreLikeDto> findStoreLikeByUser(String email){
       Long storelikeId = userRepository.findByEmail(email).getId();
       List<StoreLikeDto> storeLike = likesService.findStoreLikeByUser(storelikeId,);
       return new BaseApiResponse<>("스토어 좋아요 목록 조회 성공", storeLike);
   }*/

    //좋아요가 0인 데이터 가져오기(테스트용)
    @GetMapping("/test")
    public BaseApiResponse<Page<LikesDto>> findAllDesc(@PageableDefault(size=10) Pageable pageable){
        Page<LikesDto> newlikes = likesService.findAllDesc(pageable);
        return new BaseApiResponse<>("성공",newlikes);
    }

    //like목록 조회(test)
    @GetMapping("/find")
    public List<LikesDto> find(){
        return likesService.findAll();
    }


    //좋아요-옷
/*    @PostMapping("/dresses/{dress_id}/likes")
    public void likes(@PathVariable Long dress_id){
        likesService.likes(dress_id);
    }*/

    //   @PostMapping("/dresses/like")
    //   public void likeUpdate(Integer id){
    //    likesService.updateLike(id);
    //  }
/*    @GetMapping("/user")
    public UserDto findByEmail(String email){
        User user = userRepository.findByEmail(email);
        if(user.getEmail()==null){
            System.out.println("It's a null");
        }
        else{
            System.out.println("exist");
        }
        return new UserDto(user);
    }*/
    //옷 좋아요 목록 조회 xx
 /*   @GetMapping("/dresses/like/list")
    public List<Likes> findLikeByUserID(String email){
        Long like_id = userRepository.findByEmail(email).getId();
        return likesService.findLikeByUser(like_id);

    }*/
}
