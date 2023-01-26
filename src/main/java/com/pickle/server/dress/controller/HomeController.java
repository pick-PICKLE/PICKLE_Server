package com.pickle.server.dress.controller;

import com.pickle.server.common.util.response.BaseApiResponse;
import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.dto.DressDto;
import com.pickle.server.dress.service.HomeService;
import com.pickle.server.store.dto.StoreDto;

import com.pickle.server.store.service.StoreService;
import com.pickle.server.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final HomeService homeService;
    private final StoreService storeService;

    @GetMapping("")
    public ResponseEntity<HashMap> homeView(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
        HashMap<String, Object> homeMap = new HashMap<>();
        List<StoreDto> nearStores = storeService.getNearStores(lat, lng);
        List<DressDto> newDresses = homeService.getNewDresses(nearStores);
        List<DressDto> recDresses = homeService.getRecDresses(nearStores);

        if(newDresses.size() > 5) {
            homeMap.put("recent", "..");
            homeMap.put("new", newDresses.subList(0, 5));
            homeMap.put("recommendation", recDresses);
        } else {
            homeMap.put("recent", "..");
            homeMap.put("new", newDresses);
            homeMap.put("recommendation", recDresses);
        }

        return new ResponseEntity<>(homeMap, HttpStatus.OK);
    }

    @ApiOperation(value = "NEW 상품", notes = "새로운 상품 조회 API")
    @GetMapping("/new")
    public ResponseEntity<List<DressDto>> getNew(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
        List<StoreDto> nearStores = storeService.getNearStores(lat, lng);
        List<DressDto> newDresses = homeService.getNewDresses(nearStores);

        return new ResponseEntity<>(newDresses, HttpStatus.OK);
    }

    @GetMapping("/recommendation")
    public ResponseEntity<List<DressDto>> getRecommend(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
        List<StoreDto> nearStores = storeService.getNearStores(lat, lng);

        List<DressDto> recDresses = homeService.getRecDresses(nearStores);

        return new ResponseEntity<>(recDresses, HttpStatus.OK);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<DressDto>> getRecentView(@RequestHeader("X-ACCESS-TOKEN") String token) {
        //User user = jwtService.getUserId(token);  // jwt에서 추출
        List<DressDto> recentView = homeService.getRecentView(1L);

        return new ResponseEntity<>(recentView, HttpStatus.OK);
    }
}
