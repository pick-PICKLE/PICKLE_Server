package com.pickle.server.dress.repository;

import com.pickle.server.dress.domain.ReservedDress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservedDressRepository extends JpaRepository<ReservedDress, Long> {
}
