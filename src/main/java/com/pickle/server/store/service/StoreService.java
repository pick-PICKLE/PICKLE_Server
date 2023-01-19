package com.pickle.server.store.service;

import com.pickle.server.store.domain.Store;
import com.pickle.server.store.dto.StoreDto;
import com.pickle.server.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    /**
     * 현재 위치 기준으로 1km 이내 매장 리스트
     *
     * @param lat
     * @param lng
     * @return
     */
    public List<StoreDto> getNearStores(Double lat, Double lng) {
        List<Store> allStores = storeRepository.findAll();
        List<StoreDto> nearStores = new ArrayList<>();

        for (int i = 0; i < allStores.size(); i++) {
            Double sLat = allStores.get(i).getLatitude();
            Double SLng = allStores.get(i).getLongitude();

            // 매장과 사용자 현재 위치 사이의 거리 (meter)
            Double dist = distance(lat, lng, sLat, SLng);

            // 1km 내에 있는 매장
            if (dist <= 1000.0) {
                StoreDto storeDto = new StoreDto(allStores.get(i));
                nearStores.add(storeDto);
            }
        }

        return nearStores;
    }

    // 거리 미터 단위로 계산
    private static Double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = (dist * 180 / Math.PI);
        dist = dist * 60 * 1.1515 * 1609.344;

        return (dist);
    }

    private static Double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
}
