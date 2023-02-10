package com.pickle.server.dress.repository;

import com.pickle.server.dress.domain.DressReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DressReservationRepository  extends JpaRepository<DressReservation, Long> {
}
