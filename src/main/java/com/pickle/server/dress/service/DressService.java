package com.pickle.server.dress.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.dto.DressDetailDto;
import com.pickle.server.dress.repository.DressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DressService {
    private final DressRepository dressRepository;
    
    @Value("${secret.base-url}")
    private String baseUrl;

    public DressDetailDto findDressDetailInfoByDressId(Long dressId){
        Dress dress = dressRepository.findById(dressId).orElseThrow(
                ()->new RuntimeException("해당 id의 드레스를 찾을 수 없습니다.")
        );
        return new DressDetailDto(dress, baseUrl);
    }
}
