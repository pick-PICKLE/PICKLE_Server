package com.pickle.server.dress.repository;

import com.pickle.server.dress.domain.Dress;
import org.springframework.data.repository.query.Param;

public interface DressDslRepository {
    Dress findDressById(Long dressId);
}
