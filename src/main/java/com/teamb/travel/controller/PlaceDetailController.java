package com.teamb.travel.controller;

import com.teamb.travel.dto.place.PlaceDetailResDTO;
import com.teamb.travel.service.PlaceDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"상세페이지 API"}, description = "상세페이지 조회 API")
@RestController
@RequiredArgsConstructor
public class PlaceDetailController {

    private final PlaceDetailService placeDetailService;

    @ApiOperation(value = "상세페이지 검색", notes = "contentid에 작성되어 있는 관광지, 관광지 상세페이지 , 관광지에 작성되어 있는 리뷰를 검색한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contentid", value = "해당 관광지의 contentid" , required = true )
    })
    @GetMapping("/tourlist/detail")
    public PlaceDetailResDTO placeDetailRes(@RequestParam("contentid") String contentid) {
        return placeDetailService.getPlaceDetail(contentid);
    }
}