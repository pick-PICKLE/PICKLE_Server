package com.pickle.server.store.repository;


import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.domain.DressCategory;
import com.pickle.server.dress.dto.DressBriefDto;
import com.pickle.server.dress.dto.DressBriefInStoreDto;
import com.pickle.server.dress.dto.QDressBriefInStoreDto;
import com.pickle.server.store.dto.QStoreLikeDto;
import com.pickle.server.store.dto.StoreLikeDto;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.pickle.server.dress.domain.QDress.dress;
import static com.pickle.server.dress.domain.QDressImage.dressImage;
import static com.pickle.server.store.domain.QStore.store;
import static com.pickle.server.store.domain.QStoreLike.storeLike;


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

    @Override
    public List<StoreLikeDto> findStoreByUsers(Long userId) {
        return queryFactory
                .select(new QStoreLikeDto(
                        store,
                        store.imageUrl.prepend(keyValueService.makeUrlHead("stores"))
                ))
                .from(store, storeLike)
                .where(storeLike.user.id.eq(userId))
                .where(storeLike.store.id.eq(store.id))
                .fetch();
    }

    private JPAQuery<DressBriefInStoreDto> findDressByStoreIdOverlap(Long storeId) {

        return queryFactory
                .select(new QDressBriefInStoreDto(
                                dress.id,
                                dress.name,
                                JPAExpressions.select(dressImage.imageUrl.min().prepend(keyValueService.makeUrlHead("dresses")))
                                        .from(dressImage)
                                        .where(dressImage.dress.id.eq(dress.id)),
                                dress.price
                        )
                )
                .from(dress)
                .where(dress.store.id.eq(storeId));
    }
}
