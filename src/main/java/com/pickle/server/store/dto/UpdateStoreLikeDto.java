package com.pickle.server.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.store.domain.StoreLike;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStoreLikeDto {
    @ApiModelProperty(example = "스토어 id")
    @JsonProperty("store_id")
    private Long storeId;

    @ApiModelProperty(example = "스토어 좋아요 여부")
    @JsonProperty("is_like")
    private Boolean isLike;

    public UpdateStoreLikeDto(StoreLike storeLike) {
        this.storeId = getStoreId();
        this.isLike = getIsLike();
    }
}
