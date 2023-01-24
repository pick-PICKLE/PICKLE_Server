package com.pickle.server.dress.service;

import com.pickle.server.common.util.KeyValueService;
import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.dto.DressDetailDto;
import com.pickle.server.dress.repository.DressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DressService {
    private final DressRepository dressRepository;

    private final KeyValueService keyValueService;

    public DressDetailDto findDressDetailInfoByDressId(Long dressId){
        Dress dress = dressRepository.findById(dressId).orElseThrow(
                ()->new RuntimeException("해당 id의 드레스를 찾을 수 없습니다.")
        );
        return new DressDetailDto(dress, keyValueService.makeUrlHead("dresses"));
    }
}
