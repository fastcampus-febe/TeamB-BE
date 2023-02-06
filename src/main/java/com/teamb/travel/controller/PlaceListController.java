package com.teamb.travel.controller;

import com.teamb.travel.dto.place.PlaceListReqDTO;
import com.teamb.travel.dto.place.PlaceListResDTO;
import com.teamb.travel.service.PlaceListService;
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

@RestController
@RequiredArgsConstructor
@Slf4j
public class PlaceListController {

    private final PlaceListService placeListService;

    @GetMapping("/tourlist/keyword")
    public Result tourListByKeyWord(@RequestParam String keyword, Pageable pageable) {
        PlaceListReqDTO placeListReqDTO = new PlaceListReqDTO(keyword);

        Page<PlaceListResDTO> selectPlacesByTitleContaining = placeListService.selectPlacesByTitleContaining(placeListReqDTO, pageable);
        return new Result(selectPlacesByTitleContaining);
    }

    @GetMapping("/tourlist/location")
    public Result tourListByLocation(Pageable pageable, @RequestParam("areacode") String areaCode) {
        Page<PlaceListResDTO> selectByLocationPlaceList = placeListService.selectByLocationPlaceList(areaCode, pageable);
        return new Result(selectByLocationPlaceList);
    }

    @GetMapping("/tourlist/hashtag")
    public Result tourListByLocation(Pageable pageable, @RequestParam("hashtag") List<String> hashtag) {
        Page<PlaceListResDTO> selectByTagPlaceList = placeListService.selectByTagPlaceList(hashtag, pageable);
        return new Result(selectByTagPlaceList);
    }

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
