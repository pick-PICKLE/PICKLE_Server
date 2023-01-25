package com.pickle.server.likes.service;


import com.pickle.server.likes.dto.DressLikeDto;
import com.pickle.server.likes.dto.LikesDto;
import com.pickle.server.likes.repository.DressLikeRepository;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final UserRepository userRepository;
    private  final DressLikeRepository dressLikeRepository;
    @Transactional(readOnly = true)
    public Page<DressLikeDto> findDressLikeByUser(Long userId, Pageable pagerequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        return dressLikeRepository.findDressByUsers(userId, pagerequest);

    }

/*
    @Transactional
    public void likes(Long id) {
        likesRepository.likes(id);
    }
    @Transactional
    public void updateLike(Integer id){
        likesRepository.updateLike(id);
    }
        @Transactional(readOnly = true)
    public List<Likes> findLikeByUser(Long like_id) {
        return likesRepository.findByLike_id(like_id);
            //  return (List<DressLikesListDto>) likesRepository.findLikeByUserId(user_id);
    }
 */
//    @Transactional(readOnly = true)     //test용
//    public Page<LikesDto> findAllDesc(Pageable pagerequest) {
//        return likesRepository.findAllDesc(pagerequest).map( //findAll로 해도 될듯
//                Likes -> new LikesDto(
//                        Likes.getId().longValue(),
//                        Likes.getDress().getId(),
//                        Likes.getUser().getId()
//                ));
//    }
    //좋아요에 사용
   /* @Transactional
    public Optional<Likes> findById(Long dress_id){
        return likesRepository.findById(dress_id);
    }*/
    //
//    @Transactional(readOnly = true)     //test용
//    public List<LikesDto> findAll() {
//        return likesRepository.findAll().stream()
//                .map(LikesDto::new).collect(Collectors.toList());
//    }
    //옷 좋아요 누르기
  /*  @Transactional
    public void likesDress(Long dress_id){
        if(likesRepository.findDressLike(dress_id)!=0){
            likesRepository.delDressLikes(dress_id);
        }
        else{
            likesRepository.updateDressLikes(dress_id);
        }*/

        //     Long user_id = userRepository.findByEmail(email).getId();
        //    likesRepository.dressCart(dress_id,user_id);

//https://devofroad.tistory.com/73
 /*   @Transactional  //    https://coco-log.tistory.com/133
    public List<DressLikeDto> findDressByUser(User user){
        if(user == null){
            throw new UserNotFoundException();
        }
        Optional<User> id = userRepository.findById(user.getId());
        return likesRepository.findDressByUser(id);
    }*/
/*    @Transactional(readOnly = true)
    public Page<StoreLikeDto> findStoreLikeByUser(Long storelikeId, Pageable pagerequest){
        return likesRepository.findStoreByUser(storelikeId,pagerequest).map(
                Store -> new StoreLikeDto(
                        Store.getId(),
                        Store.getDress().getStore().getName(),
                        Store.getDress().getStore().getImage(),
                        Store.getDress().getStore().getAddress(),
                        Store.getDress().getStore().getOpenTime(),
                        Store.getDress().getStore().getCloseTime()
                )
        );
    }*/

}