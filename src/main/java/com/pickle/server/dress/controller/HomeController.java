package com.pickle.server.dress.controller;

import com.pickle.server.common.util.response.BaseApiResponse;
import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.service.HomeService;
import com.pickle.server.store.dto.StoreDto;

import com.pickle.server.store.service.StoreService;
import com.pickle.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final HomeService homeService;
    private final StoreService storeService;

    @GetMapping("/new")
    public BaseApiResponse<List<Dress>> getNew(@RequestParam double lat, @RequestParam double lng) {
        List<StoreDto> nearStores = storeService.getNearStores(lat, lng);
        List<Dress> newDresses = homeService.getNewDresses(nearStores);

        return new BaseApiResponse<>("new 목록 불러오기에 성공했습니다.", newDresses);
    }

    @GetMapping("/recommendation")
    public BaseApiResponse<List<Dress>> getRecommend(@RequestParam double lat, @RequestParam double lng) {
        List<StoreDto> nearStores = storeService.getNearStores(lat, lng);
        List<Dress> recDresses = homeService.getRecDresses(nearStores);

        return new BaseApiResponse<>("추천 목록 불러오기에 성공했습니다", recDresses);
    }

    @GetMapping("/recent")
    public BaseApiResponse<List<Dress>> getRecentView(@RequestHeader("X-ACCESS-TOKEN") String token) {
        //User user = jwtService.getUserId(token);  // jwt에서 추출해야함.
        List<Dress> recentView = homeService.getRecentView(1L);

        return new BaseApiResponse<>("최근 본 상품 불러오기에 성공했습니다", recentView);
    }
}
