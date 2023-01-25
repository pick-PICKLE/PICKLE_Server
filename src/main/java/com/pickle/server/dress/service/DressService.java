package com.pickle.server.dress.service;

import com.pickle.server.dress.repository.DressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DressService {
    private final DressRepository dressRepository;
/*
    @Transactional(readOnly = true)
    public List<Dress> findAll(Long dress_id) {
        return dressRepository.findAll(dress_id);
    }*/

}
