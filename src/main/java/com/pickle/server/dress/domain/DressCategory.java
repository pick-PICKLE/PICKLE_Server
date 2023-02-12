package com.pickle.server.dress.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DressCategory {
    ALL(Constants.all),
    OUTER(Constants.outer),
    TOP(Constants.top),
    BOTTOM(Constants.bottom),
    ONE_PIECE(Constants.onePiece),
    OTHER(Constants.other);
    private final String category;

    public static Boolean existsCategoryByName(String categoryName) {
        for (DressCategory dc : values()) {
            if (dc.category.equals(categoryName)) {
                return true;
            }
        }
        return false;
    }

    public static class Constants {
        public static final String all = "전체";
        public static final String outer = "아우터";
        public static final String top = "상의";
        public static final String bottom = "하의";
        public static final String onePiece = "원피스";
        public static final String other = "기타";
    }
}
