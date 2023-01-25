package com.pickle.server.dress.repository;

import com.pickle.server.dress.domain.Dress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DressRepository extends JpaRepository<Dress, Long> {

}