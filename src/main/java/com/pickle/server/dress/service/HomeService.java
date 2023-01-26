package com.pickle.server.dress.service;

import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.domain.RecentView;
import com.pickle.server.dress.dto.DressDto;
import com.pickle.server.dress.repository.DressRepository;
import com.pickle.server.dress.repository.RecentViewRepository;
import com.pickle.server.store.repository.StoreRepository;
import com.pickle.server.store.domain.Store;
import com.pickle.server.store.dto.StoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final DressRepository dressRepository;
    private final StoreRepository storeRepository;
    private final RecentViewRepository recentViewRepository;

    /**
     * 가까운 매장의 NEW 상품 목록
     *
     * @param nearStores
     * @return
     */
    public List<DressDto> getNewDresses(List<StoreDto> nearStores) {
        LocalDateTime stdTime = LocalDate.now().atStartOfDay().minusDays(7);
        List<DressDto> newDresses = new ArrayList<>();

        for (StoreDto storeDto : nearStores) {
            newDresses.addAll(dressRepository.findAllByCreatedAtAndStore(storeDto.getId(), stdTime));
        }

        return newDresses;
    }

    /**
     * 카테고리별 주변 매장 추천 상품
     *
     * @param nearStores
     * @return
     */
    public List<DressDto> getRecDresses(List<StoreDto> nearStores) {
        List<DressDto> recDresses = new ArrayList<>();
        List<String> Category = Arrays.asList(new String[]{"상의", "아우터", "하의", "원피스"});

        Random random = new Random();
        int index = random.nextInt(Category.size());
        System.out.println(Category.get(index));

        for (StoreDto StoreDto : nearStores) {
            recDresses.addAll(dressRepository.findRandomCategory(StoreDto.getId(), Category.get(index)));
        }
        Collections.shuffle(recDresses);

        if (recDresses.size() > 20) {
            return recDresses.subList(0, 20);
        } else {
            return recDresses;
        }
    }

    /**
     * 최근 본 상품 (DB 저장)
     *
     * @param userId
     * @return
     */
    public List<DressDto> getRecentView(Long userId) {
        List<DressDto> recentView = new ArrayList<>();
        LocalDateTime stdTime = LocalDate.now().atStartOfDay().minusMonths(1);

        recentView.addAll(recentViewRepository.findByUserId(userId, stdTime));

        return recentView;
    }
}
