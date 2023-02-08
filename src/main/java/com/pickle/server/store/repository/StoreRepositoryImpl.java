package com.pickle.server.store.repository;


import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.domain.DressCategory;
import com.pickle.server.dress.dto.DressBriefDto;
import com.pickle.server.dress.dto.DressBriefInStoreDto;
import com.pickle.server.dress.dto.QDressBriefInStoreDto;
import com.pickle.server.store.dto.QStoreLikeDto;
import com.pickle.server.store.dto.StoreLikeDto;
import com.pickle.server.store.dto.QStoreCoordDto;
import com.pickle.server.store.dto.StoreCoordDto;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
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

    @Override
    public List<StoreCoordDto> findNearStore(double latitude, double longitude) {
        return queryFactory
                .select(new QStoreCoordDto(store))
                .from(store)
                .where(calculateDistance(latitude, longitude, store.latitude, store.longitude).loe(1.5))
                .orderBy(calculateDistance(latitude, longitude,store.latitude, store.longitude).asc())
                .fetch();
    }


    private NumberExpression<Double> calculateDistance(Double doubleLat1, Double doubleLong1,
                                                       NumberPath<Double> latitude2, NumberPath<Double> longitude2) {
        NumberExpression<Double> latitude1 = latitude2.divide(latitude2).multiply(doubleLat1);
        NumberExpression<Double> theta = longitude2.add(-1 * doubleLong1).multiply(-1);
        NumberExpression<Double> degree = MathExpressions.sin(degreeToRadian(latitude1))
                .multiply(MathExpressions.sin(degreeToRadian(latitude2)))
                .add(MathExpressions.cos(degreeToRadian(latitude1))
                        .multiply(MathExpressions.cos(degreeToRadian(latitude2)))
                        .multiply(MathExpressions.cos(degreeToRadian(theta))));
        degree = MathExpressions.acos(degree);
        degree = radianToDegree(degree);
        degree = degree.multiply(60 * 1.1515 * 1609.344);

        return degree.divide(1000);
    }

    private NumberExpression<Double> degreeToRadian(NumberExpression<Double> degree) {
        return degree.multiply(Math.PI / 180.0);
    }

    private NumberExpression<Double> radianToDegree(NumberExpression<Double> radian) {
        return radian.multiply(180 / Math.PI);
    }
}
