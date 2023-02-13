package com.pickle.server.dress.controller;

import com.pickle.server.dress.dto.DressHomeDto;
import com.pickle.server.dress.dto.DressOverviewDto;
import com.pickle.server.dress.service.HomeService;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
@Api(tags = "홈")
public class HomeController {
    private final HomeService homeService;

    @ApiOperation(value = "홈 화면 조회", notes = "홈 화면 조회 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "홈 화면 조회 성공")
    })
    @GetMapping("")
    public ResponseEntity<DressHomeDto> homeView(@ApiIgnore @AuthenticationPrincipal User user, @RequestParam("lat") Double lat, @RequestParam("lng") Double lng) {
        Stream<DressOverviewDto> viewStream = homeService.getRecentView(user.getId()).stream().limit(7);
        List<DressOverviewDto> recentView = viewStream.collect(Collectors.toList());

        Stream<DressOverviewDto> newStream = homeService.getNewDresses(user.getId(), lat, lng).stream().limit(7);
        List<DressOverviewDto> newDresses = newStream.collect(Collectors.toList());

        List<DressOverviewDto> recDresses = homeService.getRecDresses(user.getId(), lat, lng);

        return new ResponseEntity<>(new DressHomeDto(recentView, newDresses, recDresses), HttpStatus.OK);
    }

    @ApiOperation(value = "NEW 상품", notes = "주변 새로운 상품 조회 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "새로운 상품 조회 성공")
    })
    @GetMapping("/new")
    public ResponseEntity<List<DressOverviewDto>> getNew(@ApiIgnore @AuthenticationPrincipal User user, @RequestParam("lat") Double lat, @RequestParam("lng") Double lng) {
        List<DressOverviewDto> newDresses = homeService.getNewDresses(user.getId(), lat, lng);

        return new ResponseEntity<>(newDresses, HttpStatus.OK);
    }

    @ApiOperation(value = "추천 상품", notes = "주변 추천 상품 조회 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "추천 상품 조회 성공")
    })
    @GetMapping("/recommendation")
    public ResponseEntity<List<DressOverviewDto>> getRecommend(@ApiIgnore @AuthenticationPrincipal User user, @RequestParam("lat") Double lat, @RequestParam("lng") Double lng) {
        List<DressOverviewDto> recDresses = homeService.getRecDresses(user.getId(), lat, lng);

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
