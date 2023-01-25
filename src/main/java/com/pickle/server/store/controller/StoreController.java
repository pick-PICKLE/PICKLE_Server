package com.pickle.server.store.controller;

import com.pickle.server.dress.domain.DressCategory;
import com.pickle.server.store.dto.StoreDetailDto;
import com.pickle.server.store.service.StoreService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    @ApiOperation(value = "스토어 상세 조회",
            httpMethod = "GET",
            response = StoreDetailDto.class,
            notes = "스토어 상세 조회 API"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "스토어 상세 조회 성공")
    })
    @GetMapping("/detail/{id}")
    public ResponseEntity<StoreDetailDto> viewDressDetail(@PathVariable("id") Long storeId,
                                                          @RequestParam(value = "category", required = false, defaultValue = DressCategory.Constants.all) String category){
        return new ResponseEntity<>(storeService.findStoreDetailInfoByStoreId(storeId, category), HttpStatus.OK);
    }
}
