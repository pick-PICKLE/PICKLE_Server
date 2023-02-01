package com.pickle.server.dress.service;

import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.domain.*;
import com.pickle.server.dress.dto.*;
import com.pickle.server.dress.repository.DressOptionDetailRepository;
import com.pickle.server.dress.repository.DressRepository;
import com.pickle.server.dress.repository.DressReservationRepository;
import com.pickle.server.store.domain.Store;
import com.pickle.server.store.repository.StoreRepository;
import com.pickle.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DressService {
    private final DressRepository dressRepository;
    private final StoreRepository storeRepository;
    private final DressReservationRepository dressReservationRepository;
    private final DressOptionDetailRepository dressOptionDetailRepository;

    private final KeyValueService keyValueService;

    public DressDetailDto findDressDetailInfoByDressId(Long dressId){
        Dress dress = dressRepository.findById(dressId).orElseThrow(
                ()->new RuntimeException("해당 id의 드레스를 찾을 수 없습니다.")
        );
        return new DressDetailDto(dress, keyValueService.makeUrlHead("dresses"));
    }

    public List<DressBriefDto> searchDress(String name, String sort, String category, Double latitude, Double longitude) {
        if(!DressCategory.findCategoryByName(category))
            throw new IllegalArgumentException("잘못된 카테고리 입니다.");

        if(!DressSortBy.findSortConditionByName(sort))
            throw new IllegalArgumentException("잘못된 정렬 입니다.");

        return dressRepository.findDressByCondition(name, sort, category, latitude, longitude);
    }

    public DressReservationFormDto getDressReservationForm(Long storeId) {
        return new DressReservationFormDto(storeRepository.findById(storeId)
                .orElseThrow(()->new RuntimeException("해당 id의 스토어를 찾을 수 없습니다.")));
    }

    public void makeDressReservation(DressReservationDto dressReservationDto, User user) {
        Store store = storeRepository.findById(dressReservationDto.getStoreId())
                .orElseThrow(()->new RuntimeException("해당 id의 스토어를 찾을 수 없습니다."));


        List<DressStock> dressStockList = new ArrayList<>();

        Dress reservedDress = dressRepository.findById(dressReservationDto.getDressId()).orElseThrow(()->new RuntimeException("해당 id의 드레스를 찾을 수 없습니다."));
        for(StockQuantityDto sqd : dressReservationDto.getReservedDressList()){
            DressOptionDetail option1 = dressOptionDetailRepository.findById(sqd.getStock1Id()).orElseThrow(() ->new RuntimeException("유효하지 않은 옵션"));
            DressOptionDetail option2 = dressOptionDetailRepository.findById(sqd.getStock2Id()).orElseThrow(() ->new RuntimeException("유효하지 않은 옵션"));
            DressStock dressStock = new DressStock(option1, option2, reservedDress, sqd.getQuantity());
            dressStockList.add(dressStock);
        }

        DressReservation dressReservation = new DressReservation(dressReservationDto, user, store, dressStockList);
        dressReservationRepository.save(dressReservation);
    }
}
