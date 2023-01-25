package com.pickle.server.dress.repository;


import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.domain.DressCategory;
import com.pickle.server.dress.domain.DressSortBy;
import com.pickle.server.dress.dto.DressBriefDto;
import com.pickle.server.dress.dto.QDressBriefDto;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.pickle.server.dress.domain.QDress.dress;
import static com.pickle.server.dress.domain.QDressImage.dressImage;

public class DressRepositoryImpl implements DressDslRepository{

    private final JPAQueryFactory queryFactory;

    private final KeyValueService keyValueService;

    public DressRepositoryImpl(EntityManager em, KeyValueService keyValueService) {
        this.queryFactory = new JPAQueryFactory(em);
        this.keyValueService = keyValueService;
    }

    @Override
    public List<DressBriefDto> findDressByCondition(String name, String sort, String category) {
        switch (name){
            case DressSortBy.Constants.price:
                return findDressByCategoryCondition(findDressByNameCondition(name), category)
                        .orderBy(dress.price.asc()).fetch();

            case DressSortBy.Constants.distance:
                return findDressByCategoryCondition(findDressByNameCondition(name), category)
                        /*현 위치와 거리 계산*/
                        .fetch();

            case DressSortBy.Constants.like:
                return findDressByCategoryCondition(findDressByNameCondition(name), category)
                        /*좋아요 개수 계산*/
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
        if(category == DressCategory.Constants.all) return nameFiltered;
        else return nameFiltered.where(dress.category.eq(category));

    }

    private JPAQuery<DressBriefDto> findDressByNameCondition(String name) {

        return queryFactory
                .select(new QDressBriefDto(
                                dress.id,
                                dress.name,
                                JPAExpressions.select(dressImage.id.stringValue().prepend(keyValueService.makeUrlHead("dresses")))
                                        .from(dressImage)
                                        .where(dressImage.id.eq(dress.id))
                                        .limit(1),
                                dress.price,
                                dress.store.name
                        )
                )
                .from(dress)
                .where(dress.name.contains(name));
    }
}
