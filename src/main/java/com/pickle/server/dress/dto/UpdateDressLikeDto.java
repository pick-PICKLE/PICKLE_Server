package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.dress.domain.DressLike;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDressLikeDto {
    @ApiModelProperty(example = "의상 id")
    @JsonProperty("dress_id")
    private Long dressId;


    public UpdateDressLikeDto(DressLike dressLike) {
        this.dressId = getDressId();

    }
}
