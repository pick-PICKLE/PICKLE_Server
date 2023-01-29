package com.pickle.server.dress.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.domain.DressImage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class DressLikeDto {
    @ApiModelProperty(example = "의상 id")
    @JsonProperty("dress_id")
    private Long dressId;
    @ApiModelProperty(example = "의상 이름")
    @JsonProperty("name")
    private String name;
    @ApiModelProperty(example = "의상 가격")
    @JsonProperty("price")
    private String price;
    @ApiModelProperty(example = "이미지 url")
    @JsonProperty("dress_image_url_list")
    private List<String> dressImageUrlList = new ArrayList<>();

    public DressLikeDto(Dress dress, String urlHead){
        DecimalFormat priceFormat = new DecimalFormat("###,###");

        this.dressId = dress.getId();
        this.name = dress.getName();
        this.price = priceFormat.format(dress.getPrice())+"원";
        for(DressImage di : dress.getImageList()){
            this.dressImageUrlList.add(urlHead + di.getId());
        }

    }
}
