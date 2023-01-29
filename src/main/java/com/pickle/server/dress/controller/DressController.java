package com.pickle.server.dress.controller;

import com.pickle.server.dress.dto.DressDetailDto;
import com.pickle.server.dress.dto.DressLikeDto;
import com.pickle.server.dress.dto.UpdateDressLikeDto;
import com.pickle.server.dress.service.DressService;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/dresses")
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
    public ResponseEntity<DressDetailDto> viewDressDetail(@PathVariable("id") Long dressId){
        return new ResponseEntity<>(dressService.findDressDetailInfoByDressId(dressId),HttpStatus.OK);
    }
    @GetMapping("/likes/{id}")
    public ResponseEntity<List<DressLikeDto>> findDressLikeByUser(@PathVariable("id") Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("해당 id의 유저를 찾을 수 없습니다."));
        return new ResponseEntity<>(dressService.findDressLikeByUser(userId),HttpStatus.valueOf(200));
    }

    @PostMapping("/likes")
    public ResponseEntity<UpdateDressLikeDto> likeDress(@RequestBody UpdateDressLikeDto updatedressLikeDto){
        dressService.likesDress(updatedressLikeDto);
        return new ResponseEntity<>(updatedressLikeDto
                , HttpStatus.valueOf(200));
    }

    @PostMapping("/likes/delete")
    public ResponseEntity<UpdateDressLikeDto> delLikeDress(@RequestBody UpdateDressLikeDto updatedressLikeDto){
        dressService.delLikeDress(updatedressLikeDto);
        return new ResponseEntity<>(updatedressLikeDto,HttpStatus.valueOf(200));
    }

}
