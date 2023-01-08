package com.pickle.server.dress.service;

import com.pickle.server.common.error.UserNotFoundException;
import com.pickle.server.dress.domain.Dress;
import com.pickle.server.dress.repository.DressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DressService {
    private final DressRepository dressRepository;
}
