package com.pickle.server.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pickle.server.store.domain.StoreLike;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateStoreLikeDto {
    @ApiModelProperty(example = "스토어 id")
    @JsonProperty("store_id")
    private Long storeId;

    @ApiModelProperty(example = "유저 id")
    @JsonProperty("user_id")
    private Long userId;

    public UpdateStoreLikeDto(StoreLike storeLike) {
        this.storeId = getStoreId();
        this.userId = getUserId();
    }
}
