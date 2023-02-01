package com.pickle.server.dress.controller;

import com.pickle.server.dress.dto.DressOverviewDto;
import com.pickle.server.dress.service.HomeService;
import com.pickle.server.store.dto.StoreCoordDto;
import com.pickle.server.store.service.StoreService;
import com.pickle.server.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
@Api(tags = "홈")
public class HomeController {
    private final HomeService homeService;
    private final StoreService storeService;

    @ApiOperation(value = "홈 화면 조회", notes = "홈 화면 조회 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "홈 화면 조회 성공")
    })
    @GetMapping("")
    public ResponseEntity<HashMap> homeView(@ApiIgnore @AuthenticationPrincipal User user, @RequestParam("lat") double lat, @RequestParam("lng") double lng) {
        HashMap<String, Object> homeMap = new HashMap<>();
        List<StoreCoordDto> nearStores = storeService.getNearStores(lat, lng);
        List<DressOverviewDto> newDresses = homeService.getNewDresses(nearStores);
        List<DressOverviewDto> recDresses = homeService.getRecDresses(nearStores);
        List<DressOverviewDto> recentView = homeService.getRecentView(user.getId());

        if(newDresses.size() > 5) {
            homeMap.put("recent", recentView);
            homeMap.put("new", newDresses.subList(0, 5));
            homeMap.put("recommendation", recDresses);
        } else {
            homeMap.put("recent", recentView);
            homeMap.put("new", newDresses);
            homeMap.put("recommendation", recDresses);
        }

        return new ResponseEntity<>(homeMap, HttpStatus.OK);
    }

    @ApiOperation(value = "NEW 상품", notes = "주변 새로운 상품 조회 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "새로운 상품 조회 성공")
    })
    @GetMapping("/new")
    public ResponseEntity<List<DressOverviewDto>> getNew(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
        List<StoreCoordDto> nearStores = storeService.getNearStores(lat, lng);
        List<DressOverviewDto> newDresses = homeService.getNewDresses(nearStores);

        return new ResponseEntity<>(newDresses, HttpStatus.OK);
    }

    @ApiOperation(value = "추천 상품", notes = "주변 추천 상품 조회 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "추천 상품 조회 성공")
    })
    @GetMapping("/recommendation")
    public ResponseEntity<List<DressOverviewDto>> getRecommend(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
        List<StoreCoordDto> nearStores = storeService.getNearStores(lat, lng);

        List<DressOverviewDto> recDresses = homeService.getRecDresses(nearStores);

        return new ResponseEntity<>(recDresses, HttpStatus.OK);
    }

    @ApiOperation(value = "최근 본 상품", notes = "최근 본 상품 조회 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "최근 본 상품 조회 성공")
    })
    @GetMapping("/recent")
    public ResponseEntity<List<DressOverviewDto>> getRecentView(@ApiIgnore @AuthenticationPrincipal User user) {
        List<DressOverviewDto> recentView = homeService.getRecentView(user.getId());

        return new ResponseEntity<>(recentView, HttpStatus.OK);
    }
}
