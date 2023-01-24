package com.pickle.server.dress.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class DressRepositoryImpl implements DressDslRepository{

    private final JPAQueryFactory queryFactory;

    public DressRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

}
