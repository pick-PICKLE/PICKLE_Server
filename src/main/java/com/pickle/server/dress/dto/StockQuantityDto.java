package com.pickle.server.dress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class StockQuantityDto {
    @ApiModelProperty(example = "주문한 상품 옵션1 id")
    @JsonProperty("stock_id")
    private Long stock1Id;

    @ApiModelProperty(example = "주문한 상품 옵션 id")
    @JsonProperty("stock_id")
    private Long stock2Id;

    @ApiModelProperty(example = "주문 수량 id")
    @JsonProperty("stock_id")
    private Integer quantity;
}
