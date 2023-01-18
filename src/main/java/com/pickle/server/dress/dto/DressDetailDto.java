package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.domain.*;
import com.pickle.server.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Setter
public class DressDetailDto {

    @JsonProperty
    private Long storeId;

    @JsonProperty("store_name")
    private String storeName;

    @JsonProperty("dress_name")
    private String dressName;

    @JsonProperty("dress_image_url_list")
    private List<String> dressImageUrlList = new ArrayList<>();

    @JsonProperty("dress_price")
    private String dressPrice;

    @JsonProperty("dress_option1")
    private DressOptionDto dressOption1;

    @JsonProperty("dress_option2")
    private DressOptionDto dressOption2;

    @JsonProperty("dress_stock")
    private List<DressStockDto> dressStockList = new ArrayList<>();

    public DressDetailDto(Dress dress, String base_url){
        Store store = dress.getStore();
        this.storeId = store.getId();
        this.storeName = store.getName();
        this.dressName = dress.getName();
        for(DressImage di : dress.getImageList()){
            this.dressImageUrlList.add(base_url+ "/" + di.getId());
        }
        DecimalFormat priceKRWFormat  = new DecimalFormat("###,###");
        this.dressPrice = priceKRWFormat.format(dress.getPrice()) + "원";
        this.dressOption1 = getDressOptionDto(dress.getDressOption1());
        this.dressOption2 = getDressOptionDto(dress.getDressOption2());
        for(DressStock ds : dress.getDressStockList()){
            this.dressStockList.add(new DressStockDto(ds));
        }
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
    @JsonProperty("dress_option_name")
    private String dressOptionName;

    @JsonProperty("dress_option_detail_list")
    private List<DressOptionDetailDto> dressOptionDetailDtoList = new ArrayList<>();
}

class DressOptionDetailDto{

    @JsonProperty("dress_option_detail_id")
    private Long dressOptionDetailId;

    @JsonProperty("dress_option_detail_name")
    private String dressOptionDetailName;

    DressOptionDetailDto(DressOptionDetail dressOptionDetail){
        this.dressOptionDetailId = dressOptionDetail.getId();
        this.dressOptionDetailName = dressOptionDetail.getName();
    }
}

class DressStockDto{

    @JsonProperty("dress_option_detail1_id")
    private Long dressOptionDetail1Id;

    @JsonProperty("dress_option_detail2_id")
    private Long dressOptionDetail2Id;

    @JsonProperty("is_in_stock")
    private Boolean isInStock;

    DressStockDto(DressStock dressStock){
        this.dressOptionDetail1Id = dressStock.getDressOptionDetail1().getId();
        this.dressOptionDetail2Id = dressStock.getDressOptionDetail2().getId();
        this.isInStock = (dressStock.getStock() > 0) ? true : false;
    }

}


