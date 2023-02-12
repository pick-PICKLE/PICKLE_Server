package com.pickle.server.dress.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DressSortBy {
    PRICE(Constants.price),
    DISTANCE(Constants.distance),
    LIKE(Constants.like),
    NEW_DRESS(Constants.newDress);

    private final String sortBy;

    public static boolean existsSortConditionByName(String sortCondition) {
        for (DressSortBy dsb : values()) {
            if (dsb.sortBy.equals(sortCondition)) {
                return true;
            }
        }
        return false;
    }

    public static class Constants {
        public static final String price = "낮은가격순";
        public static final String distance = "가까운거리순";
        public static final String like = "좋아요많은순";
        public static final String newDress = "최신순";
    }
}
