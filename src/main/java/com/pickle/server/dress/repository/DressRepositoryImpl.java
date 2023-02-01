package com.pickle.server.dress.repository;


import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.dto.DressLikeDto;
import com.pickle.server.dress.dto.QDressLikeDto;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.pickle.server.dress.domain.QDress.dress;
import static com.pickle.server.dress.domain.QDressImage.dressImage;
import static com.pickle.server.dress.domain.QDressLike.dressLike;

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
                .where(dress.id.eq(dressLike.user.id))
                .where(dressLike.user.id.eq(userId))
                .fetch();
    }
}
