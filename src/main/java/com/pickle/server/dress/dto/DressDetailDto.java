package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.domain.*;
import com.pickle.server.store.domain.Store;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Setter
public class DressDetailDto {

    @ApiModelProperty(example = "스토어 id")
    @JsonProperty("store_id")
    private Long storeId;

    @ApiModelProperty(example = "스토어 이름")
    @JsonProperty("store_name")
    private String storeName;


    @ApiModelProperty(example = "의상 id")
    @JsonProperty("dress_id")
    private Long dressId;

    @ApiModelProperty(example = "의상 이름")
    @JsonProperty("dress_name")
    private String dressName;

    @ApiModelProperty(example = "이미지 url")
    @JsonProperty("dress_image_url_list")
    private List<String> dressImageUrlList = new ArrayList<>();

    @ApiModelProperty(example = "좋아요 여부")
    @JsonProperty("is_liked")
    private Boolean isLiked;

    @ApiModelProperty(example = "의상 가격")
    @JsonProperty("dress_price")
    private Integer dressPrice;

    @ApiModelProperty(example = "의상 설명")
    @JsonProperty("comment")
    private String comment;


    @ApiModelProperty(example = "의상 옵션 1")
    @JsonProperty("dress_option1")
    private DressOptionDto dressOption1;

    @ApiModelProperty(example = "의상 옵션 2")
    @JsonProperty("dress_option2")
    private DressOptionDto dressOption2;

    @JsonProperty("dress_stock")
    private List<DressStockDto> dressStockList = new ArrayList<>();



    public DressDetailDto(Dress dress, String urlHead, Boolean isLiked){
        Store store = dress.getStore();
        this.storeId = store.getId();
        this.storeName = store.getName();
        this.dressId = dress.getId();
        this.comment = dress.getComment();
        this.dressName = dress.getName();
        for(DressImage di : dress.getImageList()){
            this.dressImageUrlList.add(urlHead + di.getImageUrl());
        }
        this.dressPrice = dress.getPrice();
        this.dressOption1 = getDressOptionDto(dress.getDressOption1());
        this.dressOption2 = getDressOptionDto(dress.getDressOption2());
        for(DressStock ds : dress.getDressStockList()){
            this.dressStockList.add(new DressStockDto(ds));
        }
        this.isLiked = isLiked;
   }

    private DressOptionDto getDressOptionDto(DressOption dressOption) {
        List<DressOptionDetailDto> dressOptionDetailDtoList = new ArrayList<>();
        for(DressOptionDetail dop : dressOption.getDressOptionDetailList()){
            dressOptionDetailDtoList.add(new DressOptionDetailDto(dop));
        }
        return new DressOptionDto(dressOption.getName(), dressOptionDetailDtoList);
    }


}

@AllArgsConstructor
class DressOptionDto{

    @ApiModelProperty(example = "드레스 옵션 이름")
    @JsonProperty("dress_option_name")
    private String dressOptionName;

    @ApiModelProperty(example = "드레스 옵션 상세 리스트")
    @JsonProperty("dress_option_detail_list")
    private List<DressOptionDetailDto> dressOptionDetailDtoList = new ArrayList<>();
}

class DressOptionDetailDto{

    @ApiModelProperty(example = "드레스 옵션 상세 id")
    @JsonProperty("dress_option_detail_id")
    private Long dressOptionDetailId;


    @ApiModelProperty(example = "드레스 옵션 상세 이름")
    @JsonProperty("dress_option_detail_name")
    private String dressOptionDetailName;

    DressOptionDetailDto(DressOptionDetail dressOptionDetail){
        this.dressOptionDetailId = dressOptionDetail.getId();
        this.dressOptionDetailName = dressOptionDetail.getName();
    }
}

class DressStockDto{

    @ApiModelProperty(example = "드레스 옵션 상세1 id")
    @JsonProperty("dress_option_detail1_id")
    private Long dressOptionDetail1Id;

    @ApiModelProperty(example = "드레스 옵션 상세2 id")
    @JsonProperty("dress_option_detail2_id")
    private Long dressOptionDetail2Id;

    @ApiModelProperty(example = "잔여 재고 여부")
    @JsonProperty("is_in_stock")
    private Boolean isInStock;

    DressStockDto(DressStock dressStock){
        this.dressOptionDetail1Id = dressStock.getDressOptionDetail1().getId();
        this.dressOptionDetail2Id = dressStock.getDressOptionDetail2().getId();
        this.isInStock = (dressStock.getStock() > 0) ? true : false;
    }

}


