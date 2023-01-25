package com.pickle.server.dress.controller;

import com.pickle.server.dress.domain.DressCategory;
import com.pickle.server.dress.domain.DressSortBy;
import com.pickle.server.dress.dto.DressBriefDto;
import com.pickle.server.dress.dto.DressDetailDto;
import com.pickle.server.dress.service.DressService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/dresses")
public class DressController {
    private final DressService dressService;

    @ApiOperation(value = "의상 상세 조회",
            httpMethod = "GET",
            response = DressDetailDto.class,
            notes = "의상 상세 조회 API"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "의상 상세 조회 성공")
    })
    @GetMapping("/detail/{id}")
    public ResponseEntity<DressDetailDto> viewDressDetail(@PathVariable("id") Long dressId){
        return new ResponseEntity<>(dressService.findDressDetailInfoByDressId(dressId),HttpStatus.OK);
    }

    @ApiOperation(value = "의상 검색",
            httpMethod = "GET",
            response = DressDetailDto.class,
            notes = "의상 검색 API"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "의상 검색 성공")
    })
    @GetMapping("/search")
    public ResponseEntity<List<DressBriefDto>> searchDress(@RequestParam("name") String name,
                                                           @RequestParam(value = "sort", required = false, defaultValue = DressSortBy.Constants.like) String sort,
                                                           @RequestParam(value = "category", required = false, defaultValue = DressCategory.Constants.all) String category){
        return null;
    }
}
