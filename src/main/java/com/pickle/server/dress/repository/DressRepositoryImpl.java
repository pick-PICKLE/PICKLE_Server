package com.pickle.server.dress.repository;


import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.domain.DressCategory;
import com.pickle.server.dress.domain.DressSortBy;
import com.pickle.server.dress.dto.DressBriefDto;
import com.pickle.server.dress.dto.QDressBriefDto;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.pickle.server.dress.dto.DressLikeDto;
import com.pickle.server.dress.dto.QDressLikeDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import java.util.List;

import static com.pickle.server.dress.domain.QDress.dress;
import static com.pickle.server.dress.domain.QDressImage.dressImage;
import static com.pickle.server.dress.domain.QDressLike.dressLike;

public class DressRepositoryImpl implements DressDslRepository {

    private final JPAQueryFactory queryFactory;
    private final KeyValueService keyValueService;
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
                .where(dress.id.eq(dressLike.user.id))
                .where(dressLike.user.id.eq(userId))
                .fetch();
    }
    
    @Override
    public List<DressBriefDto> findDressByCondition(String name, String sort, String category, Double latitude, Double longitude) {

        switch (sort) {
            case DressSortBy.Constants.price:
                return findDressByCategoryCondition(findDressByNameCondition(name), category)
                        .orderBy(dress.price.asc()).fetch();

            case DressSortBy.Constants.distance:
                if (latitude == null || longitude == null
                        || latitude > 90 || latitude < -90
                        || longitude > 180 || longitude < -180)
                    throw new RuntimeException("잘못된 위도, 경도 값입니다.");
                return findDressByCategoryCondition(findDressByNameCondition(name), category)
                        .orderBy(calculateDistance(latitude, longitude, dress.store.latitude, dress.store.longitude).asc())
                        .fetch();

            case DressSortBy.Constants.like:
                return findDressByCategoryCondition(findDressByNameCondition(name), category)
                        /*좋아요 순 정렬*/
                        .fetch();


            case DressSortBy.Constants.newDress:
                return findDressByCategoryCondition(findDressByNameCondition(name), category)
                        .orderBy(dress.createdAt.desc())
                        .fetch();
            default:
                throw new RuntimeException("잘못된 정렬 입니다.");
        }
    }

    private JPAQuery<DressBriefDto> findDressByCategoryCondition(JPAQuery<DressBriefDto> nameFiltered, String category) {
        if (category.equals(DressCategory.Constants.all)) return nameFiltered;
        else return nameFiltered.where(dress.category.eq(category));

    }

    private JPAQuery<DressBriefDto> findDressByNameCondition(String name) {

        return queryFactory
                .select(new QDressBriefDto(
                                dress.id,
                                dress.name,
                                JPAExpressions.select(dressImage.imageUrl.min().prepend(keyValueService.makeUrlHead("dresses")))
                                        .from(dressImage)
                                        .where(dressImage.dress.id.eq(dress.id)),
                                dress.price,
                                dress.store.name
                        )
                )
                .from(dress)
                .where(dress.name.contains(name));
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
