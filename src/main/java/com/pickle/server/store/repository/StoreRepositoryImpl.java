package com.pickle.server.store.repository;


import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.dto.DressBriefDto;
import com.pickle.server.dress.dto.QDressBriefDto;
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
    public List<DressBriefDto> findDressDtoByStoreId(Long storeId) {
        return findDressByStoreIdOverlap(storeId).fetch();
    }

    @Override
    public List<DressBriefDto> findDressDtoByStoreIdAndCategory(Long storeId, String category) {
        return findDressByStoreIdOverlap(storeId)
                .where(dress.category.eq(category))
                .fetch();
    }


    private JPAQuery<DressBriefDto> findDressByStoreIdOverlap(Long storeId) {

        return queryFactory
                .select(new QDressBriefDto(
                                dress.id,
                                dress.name,
                                JPAExpressions.select(dressImage.id.stringValue().prepend(keyValueService.makeUrlHead("dresses")))
                                        .from(dressImage)
                                        .where(dressImage.id.eq(dress.id))
                                        .limit(1),
                                dress.price
                        )
                )
                .from(dress)
                .where(dress.store.id.eq(storeId));
    }
}
