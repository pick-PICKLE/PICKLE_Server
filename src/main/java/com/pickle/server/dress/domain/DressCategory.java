package com.pickle.server.dress.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DressCategory {
    OUTER("아우터"),
    TOP("상의"),
    BOTTOM("하의"),
    ONE_PIECE("원피스"),
    OTHER("기타");
    private final String category;

    public static Boolean findCategoryByName(String categoryName) {
        System.out.println(categoryName);
        for (DressCategory dc : values()) {
            if (dc.category.equals(categoryName)) {
                return true;
            }
        }
        return false;
    }
}
