package com.pickle.server.dress.controller;

import com.pickle.server.dress.service.DressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/dresses")
public class DressController {
    private final DressService dressService;

}
