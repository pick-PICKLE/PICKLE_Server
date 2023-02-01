package com.pickle.server.store.controller;

import com.pickle.server.dress.domain.DressCategory;
import com.pickle.server.store.dto.StoreDetailDto;
import com.pickle.server.store.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.pickle.server.store.dto.StoreDetailDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
@Api(tags = "매장")
public class StoreController {
    private final StoreService storeService;

    /**
     * 가까운 매장 조회
     * @param lat
     * @param lng
     * @return
     */
    @ApiOperation(value = "근처 스토어 조회", notes = "근처 스토어 조회 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "근처 스토어 조회 성공")
    })
    @GetMapping("/near")
    public ResponseEntity<List<StoreCoordDto>> getNearStores(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
        List<StoreCoordDto> nearStores = storeService.getNearStores(lat, lng);

        return new ResponseEntity<>(nearStores, HttpStatus.OK);
    }

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
        System.out.println("들어왔나요?");
        return new ResponseEntity<>(storeService.findStoreDetailInfoByStoreId(storeId, category), HttpStatus.OK);
    }


}
