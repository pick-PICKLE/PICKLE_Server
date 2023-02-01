package com.pickle.server.dress.service;

import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.domain.RecentView;
import com.pickle.server.dress.dto.DressOverviewDto;
import com.pickle.server.dress.repository.DressRepository;
import com.pickle.server.dress.repository.RecentViewRepository;
import com.pickle.server.store.repository.StoreRepository;
import com.pickle.server.store.dto.StoreCoordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final DressRepository dressRepository;
    private final RecentViewRepository recentViewRepository;
    private final KeyValueService keyValueService;

    public List<DressOverviewDto> getNewDresses(List<StoreCoordDto> nearStores) {
        LocalDateTime stdTime = LocalDate.now().atStartOfDay().minusDays(7);
        List<Dress> allnewDresses = new ArrayList<>();
        List<DressOverviewDto> newDresses = new ArrayList<>();

        for (StoreCoordDto storeCoordDto : nearStores) {
            allnewDresses.addAll(dressRepository.findAllByCreatedAtAndStore(storeCoordDto.getId(), stdTime));
        }
        Collections.sort(allnewDresses, new Comparator<Dress>(){
            @Override
            public int compare(Dress d1, Dress d2) {
                if(d1.getCreatedAt().isBefore(d2.getCreatedAt())) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        for (Dress dress : allnewDresses) {
            newDresses.add(new DressOverviewDto(dress, keyValueService.makeUrlHead("dresses")));
        }

        return newDresses;
    }

    public List<DressOverviewDto> getRecDresses(List<StoreCoordDto> nearStores) {
        List<DressOverviewDto> recDressesOverview = new ArrayList<>();
        List<Dress> recDresses = new ArrayList<>();
        List<String> Category = Arrays.asList(new String[]{"상의", "아우터", "하의"});

        Random random = new Random();
        int index = random.nextInt(Category.size());
        System.out.println(Category.get(index));

        for (StoreCoordDto StoreCoordDto : nearStores) {
            recDresses.addAll(dressRepository.findRandomCategory(StoreCoordDto.getId(), Category.get(index)));
        }
        for (Dress dress : recDresses) {
            recDressesOverview.add(new DressOverviewDto(dress, keyValueService.makeUrlHead("dresses")));
        }
        Collections.shuffle(recDressesOverview);

        if (recDressesOverview.size() > 20) {
            return recDressesOverview.subList(0, 20);
        } else {
            return recDressesOverview;
        }
    }

    public List<DressOverviewDto> getRecentView(Long userId) {
        List<RecentView> recent = new ArrayList<>();
        List<DressOverviewDto> recentView = new ArrayList<>();
        LocalDateTime stdTime = LocalDate.now().atStartOfDay().minusMonths(1);

        recent.addAll(recentViewRepository.findByUserId(userId, stdTime));

        for (RecentView r : recent) {
            recentView.add(new DressOverviewDto(r.getDress(),keyValueService.makeUrlHead("dresses")));
        }
        return recentView;
    }
}
