package com.pickle.server.store.repository;

import com.pickle.server.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>, StoreDslRepository, QuerydslPredicateExecutor<Store> {

}

