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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final DressRepository dressRepository;
    private final RecentViewRepository recentViewRepository;
    private final KeyValueService keyValueService;

    public List<DressOverviewDto> getNewDresses(Long userId, Double lat, Double lng) {
        LocalDateTime stdTime = LocalDate.now().atStartOfDay().minusDays(7);

        Stream<DressOverviewDto> newStream = dressRepository.findDressByStoreAndCreatedAt(userId, lat, lng, stdTime).stream().limit(30);
        List<DressOverviewDto> newDresses = newStream.collect(Collectors.toList());

        return newDresses;
    }

    public List<DressOverviewDto> getRecDresses(Long userId, Double lat, Double lng) {
        List<String> Category = Arrays.asList(new String[]{"상의", "아우터", "하의", "원피스", "기타"});

        Random random = new Random();
        int index = random.nextInt(Category.size());

        Stream<DressOverviewDto> recStream = dressRepository.findDressByCategory(userId, Category.get(index), lat, lng).stream().limit(20);
        List<DressOverviewDto> recDresses = recStream.collect(Collectors.toList());

        Collections.shuffle(recDresses);

        return recDresses;
    }

    public List<DressOverviewDto> getRecentView(Long userId) {
        LocalDateTime stdTime = LocalDate.now().atStartOfDay().minusMonths(1);

        Stream<DressOverviewDto> viewStream = dressRepository.findDressByRecentView(userId, stdTime).stream().limit(30);
        List<DressOverviewDto> recentView = viewStream.collect(Collectors.toList());

        return recentView;
    }
}
