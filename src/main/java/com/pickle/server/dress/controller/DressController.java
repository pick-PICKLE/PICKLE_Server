package com.pickle.server.dress.controller;

import com.pickle.server.dress.dto.DressDetailDto;
import com.pickle.server.dress.service.DressService;
import com.pickle.server.user.domain.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


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
    public ResponseEntity<DressDetailDto> viewDressDetail(@PathVariable("id") Long dressId, @ApiIgnore @AuthenticationPrincipal User user){
        return new ResponseEntity<>(dressService.findDressDetailInfoByDressId(dressId, user),HttpStatus.OK);
    }
}
