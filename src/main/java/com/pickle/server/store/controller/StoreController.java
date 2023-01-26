package com.pickle.server.store.controller;

import com.pickle.server.store.dto.StoreDto;
import com.pickle.server.store.service.StoreService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    /**
     * 가까운 매장 조회
     * @param lat
     * @param lng
     * @return
     */
    @GetMapping("/near")
    public ResponseEntity<List<StoreDto>> getNearStores(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
        List<StoreDto> nearStores = storeService.getNearStores(lat, lng);

        return new ResponseEntity<>(nearStores, HttpStatus.OK);
    }
}
