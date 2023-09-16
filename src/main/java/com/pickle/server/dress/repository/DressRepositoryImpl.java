package com.pickle.server.dress.repository;


import com.pickle.server.common.error.CustomException;
import com.pickle.server.common.error.ErrorResponseStatus;
import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.domain.*;
import com.pickle.server.dress.dto.*;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.pickle.server.dress.domain.QDress.dress;
import static com.pickle.server.dress.domain.QDressImage.dressImage;
import static com.pickle.server.dress.domain.QDressLike.dressLike;
import static com.pickle.server.dress.domain.QDressReservation.dressReservation;
import static com.pickle.server.dress.domain.QRecentView.recentView;


public class DressRepositoryImpl implements DressDslRepository {

    private final JPAQueryFactory queryFactory;
    private final KeyValueService keyValueService;

    public DressRepositoryImpl(EntityManager em, KeyValueService keyValueService) {
        this.queryFactory = new JPAQueryFactory(em);
        this.keyValueService = keyValueService;
    }

    
    @Override
    public List<DressLikeDto> findDressByUsers(Long userId) {
        return queryFactory
                .select(new QDressLikeDto(
                        dress,
                        JPAExpressions.select(dressImage.imageUrl.min().prepend(keyValueService.makeUrlHead("dresses")))
                                .from(dressImage)
                                .where(dressImage.dress.id.eq(dress.id))
                ))
                .from(dress, dressLike)
                .where(dressLike.user.id.eq(userId))
                .where(dressLike.dress.id.eq(dress.id))
                .fetch();
    }

    @Override
    public List<DressBriefDto> findDressByCondition(String name, String sort, String category,
                                                    Double latitude, Double longitude, Long userId) {

        switch (sort) {
            case DressSortBy.Constants.price:
                return findDressByCategoryCondition(findDressByNameCondition(name, userId, latitude, longitude), category)
                        .orderBy(dress.price.asc()).fetch();

            case DressSortBy.Constants.distance:
                if (latitude == null || longitude == null
                        || latitude > 90 || latitude < -90
                        || longitude > 180 || longitude < -180)
                    throw new CustomException(ErrorResponseStatus.BAD_REQUEST_INVALID_SEARCH_PARAMETER);
                return findDressByCategoryCondition(findDressByNameCondition(name, userId, latitude, longitude), category)
                        .orderBy(calculateDistance(latitude, longitude, dress.store.latitude, dress.store.longitude).asc())
                        .fetch();

            case DressSortBy.Constants.like:
                return findDressByCategoryCondition(findDressByNameCondition(name, userId, latitude, longitude), category)
                        .leftJoin(dressLike).on(dress.id.eq(dressLike.dress.id))
                        .groupBy(dress.id)
                        .orderBy(dressLike.count().desc())
                        .fetch();

            case DressSortBy.Constants.newDress:
                return findDressByCategoryCondition(findDressByNameCondition(name, userId, latitude, longitude), category)
                        .orderBy(dress.createdAt.desc())
                        .fetch();
            default:
                throw new CustomException(ErrorResponseStatus.BAD_REQUEST_INVALID_SEARCH_PARAMETER);
        }
    }

    private JPAQuery<DressBriefDto> findDressByCategoryCondition(JPAQuery<DressBriefDto> nameFiltered, String category) {
        if (category.equals(DressCategory.Constants.all)) return nameFiltered;
        else return nameFiltered.where(dress.category.eq(category));

    }

    private JPAQuery<DressBriefDto> findDressByNameCondition(String name, Long userId, Double latitude, Double longitude) {

        return queryFactory
                .select(new QDressBriefDto(
                                dress.id,
                                dress.name,
                                JPAExpressions.select(dressImage.imageUrl.min().prepend(keyValueService.makeUrlHead("dresses")))
                                        .from(dressImage)
                                        .where(dressImage.dress.id.eq(dress.id)),
                                dress.price,
                                dress.store.name,
                                dress.store.id,
                                JPAExpressions.select(dressLike)
                                        .from(dressLike)
                                        .where(dressLike.dress.id.eq(dress.id)
                                                .and(dressLike.user.id.eq(userId)))
                        )
                )
                .from(dress)
                .where(dress.name.contains(name)
                        .and(calculateDistance(latitude, longitude, dress.store.latitude, dress.store.longitude).lt(2)));
    }

    @Override
    public List<DressOverviewDto> findDressByRecentView(Long userId, LocalDateTime stdDate){
        return queryFactory
                .select(new QDressOverviewDto(
                        dress,
                        dressLike.id,
                        JPAExpressions.select(dressImage.imageUrl.min().prepend(keyValueService.makeUrlHead("dresses")))
                                .from(dressImage)
                                .where(dressImage.dress.id.eq(dress.id))
                                .limit(1)
                ))
                .from(dress)
                .join(recentView).on(dress.id.eq(recentView.dress.id)
                        .and(recentView.user.id.eq(userId)))
                .where(recentView.createdAt.goe(stdDate))
                .leftJoin(dressLike).on(dress.id.eq(dressLike.dress.id).and(dressLike.user.id.eq(userId)))
                .orderBy(recentView.modifiedAt.desc())
                .fetch();
    }

    @Override
    public List<DressOverviewDto> findDressByStoreAndCreatedAt(Long userId, Double latitude, Double longitude, LocalDateTime stdDate) {
        return queryFactory
                .select(new QDressOverviewDto(
                        dress,
                        dressLike.id,
                        JPAExpressions.select(dressImage.imageUrl.min().prepend(keyValueService.makeUrlHead("dresses")))
                                .from(dressImage)
                                .where(dressImage.dress.id.eq(dress.id))
                                .limit(1)
                ))
                .from(dress)
                .where(calculateDistance(latitude, longitude, dress.store.latitude, dress.store.longitude).loe(1.0)
                        .and(dress.createdAt.goe(stdDate)))
                .orderBy(dress.createdAt.desc())
                .leftJoin(dressLike).on(dress.id.eq(dressLike.dress.id).and(dressLike.user.id.eq(userId)))
                .fetch();
    }

    @Override
    public List<DressOverviewDto> findDressByCategory(Long userId, String category, Double latitude, Double longitude) {
        return queryFactory
                .select(new QDressOverviewDto(
                        dress,
                        dressLike.id,
                        JPAExpressions.select(dressImage.imageUrl.min().prepend(keyValueService.makeUrlHead("dresses")))
                                .from(dressImage)
                                .where(dressImage.dress.id.eq(dress.id))
                                .limit(1)
                ))
                .from(dress)
                .leftJoin(dressLike).on(dress.id.eq(dressLike.dress.id).and(dressLike.user.id.eq(userId)))
                .where(calculateDistance(latitude, longitude, dress.store.latitude, dress.store.longitude).loe(1.0)
                        .and(dress.category.eq(category)))
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

    @Override
    public List<DressOrderDto> findReservationByUserAndReservationId(Long dressReservationId, Long userId) {
        return queryFactory
                .select(new QDressOrderDto(
                        dressReservation, Expressions.asString(keyValueService.makeUrlHead("dresses")))
                )
                .from(dressReservation)
                .where(dressReservation.user.id.eq(userId).and(dressReservation.id.eq(dressReservationId)))
                .fetch();
    }

    @Override
    public List<DressOrderListDto> findReservationListByStatusAndUser(String status,Long userId) {
        if(!DressReservationStatus.existsDressReservationStatusByName(status))
            throw new CustomException(ErrorResponseStatus.BAD_REQUEST_INVALID_SEARCH_PARAMETER);

        return queryFactory
                .select(new QDressOrderListDto(
                        dressReservation, Expressions.asString(keyValueService.makeUrlHead("dresses"))
                ))
                .from(dressReservation)
                .where(dressReservation.user.id.eq(userId))
                .where(dressReservation.status.eq(status))
                .fetch();
    }
}
