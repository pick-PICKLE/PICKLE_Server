package com.pickle.server.dress.service;

import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.domain.RecentView;
import com.pickle.server.dress.dto.DressOverviewDto;
import com.pickle.server.dress.repository.DressRepository;
import com.pickle.server.dress.repository.RecentViewRepository;
import com.pickle.server.store.dto.StoreCoordDto;
import com.pickle.server.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final DressRepository dressRepository;
    private final RecentViewRepository recentViewRepository;
    private final KeyValueService keyValueService;

    public List<DressOverviewDto> getNewDresses(Long userId, Double lat, Double lng) {
        LocalDateTime stdTime = LocalDate.now().atStartOfDay().minusDays(7);
        List<DressOverviewDto> newDresses = new ArrayList<>();
        newDresses.addAll(dressRepository.findDressByStoreAndCreatedAt(userId, lat, lng, stdTime));

        return newDresses;
    }

    public List<DressOverviewDto> getRecDresses(Long userId, Double lat, Double lng) {
        List<DressOverviewDto> recDresses = new ArrayList<>();
        List<String> Category = Arrays.asList(new String[]{"상의", "아우터", "하의", "원피스", "기타"});

        Random random = new Random();
        int index = random.nextInt(Category.size());
        System.out.println(Category.get(index));

        recDresses.addAll(dressRepository.findDressByCategory(userId, Category.get(index), lat, lng));

        Collections.shuffle(recDresses);

        if (recDresses.size() > 20) {
            return recDresses.subList(0, 20);
        } else {
            return recDresses;
        }
    }

    public List<DressOverviewDto> getRecentView(Long userId) {
        List<DressOverviewDto> recentView = new ArrayList<>();
        LocalDateTime stdTime = LocalDate.now().atStartOfDay().minusMonths(1);

        recentView.addAll(dressRepository.findDressByRecentView(userId, stdTime));

        return recentView;
    }
}
