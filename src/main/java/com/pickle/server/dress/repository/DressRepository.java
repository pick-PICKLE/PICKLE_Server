package com.pickle.server.dress.repository;

import com.pickle.server.dress.domain.Dress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DressRepository extends JpaRepository<Dress, Long> {
    /*@Query(value="select name,image,price from dress where dress_id=?",nativeQuery = true)
    List<Dress> findAll(Long dress_id);*/
}