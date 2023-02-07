package com.teamb.travel.controller;

import com.teamb.travel.dto.place.PlaceListReqDTO;
import com.teamb.travel.dto.place.PlaceListResDTO;
import com.teamb.travel.service.PlaceListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"관광지 리스트 API"}, description = "검색 조건에 맞는 관광지들을 List로 반환한다")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PlaceListController {

    private final PlaceListService placeListService;

    @ApiOperation(value = "키워드(관광지명)로 검색", notes = "해당 관광지명이 포함된 모든 관광지를 오름차순으로 반환한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "키워드(관광지명)", required = true),
            @ApiImplicitParam(name = "pageno", value = "페이지 넘버"),
            @ApiImplicitParam(name = "size", value = "페이지에 들어오는 데이터 갯수"),
    })
    @GetMapping("/tourlist/keyword")
    public Result tourListByKeyWord(@RequestParam String keyword, Pageable pageable) {
        PlaceListReqDTO placeListReqDTO = new PlaceListReqDTO(keyword);

        Page<PlaceListResDTO> selectPlacesByTitleContaining = placeListService.selectPlacesByTitleContaining(placeListReqDTO, pageable);
        return new Result(selectPlacesByTitleContaining);
    }

    @ApiOperation(value = "지역코드로 검색", notes = "지역코드로 검색된 관광지, 관광지 평점, 실내외여부를 가나다 오름차순으로 호출한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areacode", value = "지역코드", required = true),
            @ApiImplicitParam(name = "pageno", value = "페이지 넘버"),
            @ApiImplicitParam(name = "size", value = "페이지에 들어오는 데이터 갯수"),
    })
    @GetMapping("/tourlist/location")
    public Result tourListByLocation(Pageable pageable, @RequestParam("areacode") String areaCode) {
        Page<PlaceListResDTO> selectByLocationPlaceList = placeListService.selectByLocationPlaceList(areaCode, pageable);
        return new Result(selectByLocationPlaceList);
    }

    @ApiOperation(value = "해시태그로 검색", notes = "해시태그로 검색된 관광지, 관광지 평점, 실내외여부를 가나다 오름차순으로 호출한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hashtag", value = "해시태그", required = true),
            @ApiImplicitParam(name = "pageno", value = "페이지 넘버"),
            @ApiImplicitParam(name = "size", value = "페이지에 들어오는 데이터 갯수"),
    })
    @GetMapping("/tourlist/hashtag")
    public Result tourListByLocation(Pageable pageable, @RequestParam("hashtag") List<String> hashtag) {
        Page<PlaceListResDTO> selectByTagPlaceList = placeListService.selectByTagPlaceList(hashtag, pageable);
        return new Result(selectByTagPlaceList);
    }

    @ApiOperation(value = "관광지 검색", notes = "모든 관광지, 관광지 평점, 실내외여부를 가나다 오름차순으로 호출한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "페이지 넘버" ),
            @ApiImplicitParam(name = "size", value = "페이지에 들어오는 데이터 갯수"),
    })
    @GetMapping("/tourlist")
    public Result mainPlace(Pageable pageable) {
        Page<PlaceListResDTO> tourlist = placeListService.mainPlaceTourlist(pageable);
        return new Result(tourlist);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

}
