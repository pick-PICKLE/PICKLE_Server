package com.pickle.server.likes.dto;
import com.pickle.server.likes.domain.Likes;
import com.pickle.server.dress.domain.Dress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DressLikeListDto {
    private Long like_id;
    private Long user_id;
    private Long dress_id;
    private String name;
    private Integer price;
    private String image;

  //  public DressLikesListDto(Long like_id,Long user_id,Long dress_id,String name,
     //                        Integer price, String image){
    /*
     public DressLikeListDto(Dress Entity){
         this.user_id = getUser_id();
         this.like_id = getLike_id();
         this.dress_id = getDress_id();
         this.name = getName();
         this.price = getPrice();
         this.image = getImage();
    }*/
    public DressLikeListDto fromEntity(Dress dress, Likes likes){
        return DressLikeListDto.builder()
                .user_id(likes.getUser().getId())
                .like_id(likes.getId().longValue())
                .dress_id(likes.getDress().getId())
                .name(dress.getName())
                .price(dress.getPrice())
                .image(dress.getImage())
                .build();
    }
   // private List<Dress> dress = new ArrayList<>();




}
