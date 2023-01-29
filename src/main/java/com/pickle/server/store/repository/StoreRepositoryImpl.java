package com.pickle.server.store.repository;


import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.domain.DressCategory;
import com.pickle.server.dress.dto.DressBriefInStoreDto;
import com.pickle.server.dress.dto.QDressBriefInStoreDto;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.pickle.server.dress.domain.QDress.dress;
import static com.pickle.server.dress.domain.QDressImage.dressImage;


public class StoreRepositoryImpl implements StoreDslRepository {

    private final JPAQueryFactory queryFactory;

    private final KeyValueService keyValueService;

    public StoreRepositoryImpl(EntityManager em, KeyValueService keyValueService) {
        this.queryFactory = new JPAQueryFactory(em);
        this.keyValueService = keyValueService;
    }

    @Override
    public List<DressBriefInStoreDto> findDressDtoByStoreIdAndCategory(Long storeId, String category) {
        if (category.equals(DressCategory.Constants.all))
            return findDressByStoreIdOverlap(storeId).fetch();

        else
            return findDressByStoreIdOverlap(storeId)
                    .where(dress.category.eq(category))
                    .fetch();
    }


    private JPAQuery<DressBriefInStoreDto> findDressByStoreIdOverlap(Long storeId) {

        return queryFactory
                .select(new QDressBriefInStoreDto(
                                dress.id,
                                dress.name,
                                JPAExpressions.select(dressImage.id.min().stringValue().prepend(keyValueService.makeUrlHead("dresses")))
                                        .from(dressImage)
                                        .where(dressImage.dress.id.eq(dress.id)),
                                dress.price
                        )
                )
                .from(dress)
                .where(dress.store.id.eq(storeId));
    }
}
