package com.pickle.server.dress.dto;

import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.domain.DressImage;
import com.pickle.server.store.domain.Store;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DressDetailDto {
    private Long storeId;
    private String storeName;
    private String dressName;
    private List<String> dressImageList = new ArrayList<>();
    private String dressPrice;
    private LinkedHashMap<String, List<DressOptionDetailDto>> dressOption1 = new LinkedHashMap<>();
    private LinkedHashMap<String, List<DressOptionDetailDto>> dressOption2 = new LinkedHashMap<>();

    public DressDetailDto(Dress dress){
        Store store = dress.getStore();
        this.storeId = store.getId();
        this.storeName = store.getName();
        for(DressImage dressImage : dress.getImageList()){
            this.dressImageList.add(String.valueOf(dressImage.getId()));
        }
        this.dressPrice = String.valueOf(dress.getPrice()) + "원";
   }
}

class DressOptionDetailDto{
    private Long dressOptionDetailId;
    private String dressOptionDetailName;
}

