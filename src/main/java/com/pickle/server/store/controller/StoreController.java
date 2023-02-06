package com.pickle.server.store.controller;

import com.pickle.server.dress.domain.DressCategory;
import com.pickle.server.store.dto.StoreCoordDto;
import com.pickle.server.store.dto.StoreDetailDto;
import com.pickle.server.store.dto.StoreLikeDto;
import com.pickle.server.store.dto.UpdateStoreLikeDto;
import com.pickle.server.store.service.StoreService;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.repository.UserRepository;
import io.swagger.annotations.Api;
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
@RequestMapping("/stores")
@Api(tags = "매장")
public class StoreController {
    private final UserRepository userRepository;
    private final StoreService storeService;

    @ApiOperation(value = "스토어 상세 조회",
            httpMethod = "GET",
            response = StoreDetailDto.class,
            notes = "스토어 상세 조회 API"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "스토어 상세 조회 성공")
    })
    @GetMapping("/detail/{id}")
    public ResponseEntity<StoreDetailDto> viewDressDetail(@PathVariable("id") Long storeId,
                                                          @RequestParam(value = "category", required = false, defaultValue = DressCategory.Constants.all) String category){
        System.out.println("들어왔나요?");
        return new ResponseEntity<>(storeService.findStoreDetailInfoByStoreId(storeId, category), HttpStatus.OK);
    }


    @ApiOperation(value = "스토어 좋아요 추가",
            httpMethod = "POST",
            response = UpdateStoreLikeDto.class,
            notes = "스토어 좋아요 추가 API"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "스토어 좋아요 추가 성공")})
    @PostMapping("/likes")
    public ResponseEntity<UpdateStoreLikeDto> likeStore(@RequestBody UpdateStoreLikeDto updatestoreLikeDto){
        storeService.likesStore(updatestoreLikeDto);
        return new ResponseEntity<>(updatestoreLikeDto
                , HttpStatus.OK);
    }

    @ApiOperation(value = "스토어 좋아요 목록 조회",
            httpMethod = "GET",
            response = StoreLikeDto.class,
            notes = "스토어 좋아요 목록 조회 API"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "스토어 좋아요 목록 조회 성공")})
    @GetMapping("/likes/{id}")
    public ResponseEntity<List<StoreLikeDto>> findStoreLikeByUser(@PathVariable("id") Long userId){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        return new ResponseEntity<>(storeService.findStoreLikeByUser(userId),HttpStatus.OK);
    }

    @ApiOperation(value ="스토어 좋아요 삭제",
            httpMethod = "POST",
            response = UpdateStoreLikeDto.class,
            notes ="스토어 좋아요 삭제 API"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "스토어 좋아요 삭제 성공")})
    @PostMapping("/likes/delete")
    public ResponseEntity<UpdateStoreLikeDto> delLikeStore(@RequestBody UpdateStoreLikeDto updateStoreLikeDto){
        storeService.delLikeStore(updateStoreLikeDto);
        return new ResponseEntity<>(updateStoreLikeDto,
                HttpStatus.OK);
    }

    @ApiOperation(value ="근처 스토어 조회", notes ="스토어 좋아요 삭제 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "근처 매장 조회 성공")
    })
    @GetMapping ("/near")
    public ResponseEntity<List<StoreCoordDto>> nearStores(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
        List<StoreCoordDto> nearStores = storeService.getNearStores(lat, lng);

        return new ResponseEntity<>(nearStores, HttpStatus.OK);
    }
}
