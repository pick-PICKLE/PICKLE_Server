package com.pickle.server.likes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.likes.domain.DressLike;
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

    @ApiModelProperty(example = "의상 id")
    @JsonProperty("user_id")
    private Long userId;

    public UpdateDressLikeDto(DressLike dressLike) {
        this.dressId = getDressId();
        this.userId = getUserId();
    }
}
