package com.pickle.server.dress.repository;


import com.pickle.server.dress.domain.Dress;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.pickle.server.dress.domain.QDress.dress;

public class DressRepositoryImpl implements DressDslRepository{

    private final JPAQueryFactory queryFactory;

    public DressRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Dress findDressById(Long id) {
        return queryFactory
                .selectFrom(dress)
                .where(dress.id.eq(id))
                .fetchOne();
    }
}
