package com.pickle.server.dress.controller;

import com.pickle.server.config.PropertyUtil;
import com.pickle.server.dress.domain.DressCategory;
import com.pickle.server.dress.domain.DressSortBy;
import com.pickle.server.dress.dto.DressDetailDto;
import com.pickle.server.dress.dto.DressLikeDto;
import com.pickle.server.dress.dto.UpdateDressLikeDto;
import com.pickle.server.dress.service.DressService;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/dresses")
@Api(tags = "의상")
public class DressController {
    @Autowired
    private final DressService dressService;
    private final UserRepository userRepository;

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



    @ApiOperation(value = "의상 검색",
            httpMethod = "GET",
            response = DressDetailDto.class,
            notes = "카테고리 : 아우터, 상의, 하의, 원피스, 기타, (미입력시)전체\n"
            + "정렬 : 낮은가격순, 가까운거리순, (미입력시)좋아요많은순, 최신순\n"
            + "의상 검색 API"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "의상 검색 성공")
    })
    @GetMapping("/search")
    public ResponseEntity<JSONObject> searchDress(@RequestParam("name") String name,
                                                  @RequestParam(value = "sort", required = false, defaultValue = DressSortBy.Constants.like) String sort,
                                                  @RequestParam(value = "category", required = false, defaultValue = DressCategory.Constants.all) String category,
                                                  @RequestParam(value = "latitude", required = false) Double latitude,
                                                  @RequestParam(value = "longitude", required = false) Double longitude){

        return new ResponseEntity<>(
                PropertyUtil.response(dressService.searchDress(name, sort, category, latitude, longitude))
                , HttpStatus.OK);
    }


    @ApiOperation(value="의상 좋아요 조회",
            httpMethod = "GET",
            response = DressLikeDto.class,
            notes = "좋아요 조회 API"
    )
    @ApiResponses({
            @ApiResponse(code=200, message= "의상 좋아요 목록 조회 성공")
    })
    
    @GetMapping("/likes/{id}")
    public ResponseEntity<List<DressLikeDto>> findDressLikeByUser(@PathVariable("id") Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        return new ResponseEntity<>(dressService.findDressLikeByUser(userId),HttpStatus.OK);
    }

    @ApiOperation(value="의상 좋아요",
            httpMethod = "POST",
            response = UpdateDressLikeDto.class,
            notes = "의상 좋아요 추가/삭제 API"
    )
    @ApiResponses({
            @ApiResponse(code=200, message= "의상 좋아요 추가/삭제 성공")
    })
    @PostMapping("/likes")
    public ResponseEntity<UpdateDressLikeDto> likeDress(@RequestBody UpdateDressLikeDto updatedressLikeDto){
        dressService.likesDress(updatedressLikeDto);
        return new ResponseEntity<>(updatedressLikeDto
                , HttpStatus.OK);
    }
/*
    @ApiOperation(value="의상 좋아요 삭제",
            httpMethod = "POST",
            response = UpdateDressLikeDto.class,
            notes = "의상 좋아요 삭제 API"
    )
    @ApiResponses({
            @ApiResponse(code=200, message= "의상 좋아요 삭제 성공")
    })
    @PostMapping("/likes/delete")
    public ResponseEntity<UpdateDressLikeDto> delLikeDress(@RequestBody UpdateDressLikeDto updatedressLikeDto){
        dressService.delLikeDress(updatedressLikeDto);
        return new ResponseEntity<>(updatedressLikeDto,HttpStatus.OK);
    }*/

}
