package com.teamb.travel.controller;

import com.teamb.travel.dto.place.PlaceListReqDTO;
import com.teamb.travel.dto.place.PlaceListResDTO;
import com.teamb.travel.service.PlaceListService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class PlaceListController {

    @Autowired
    PlaceListService placeListService;

    @GetMapping("/tourlist/keyword")
    public Result tourListByKeyWord(@RequestParam String keyword, Pageable pageable) {

        PlaceListReqDTO placeListReqDTO = new PlaceListReqDTO(keyword);

        List<PlaceListResDTO> resDtos = placeListService.selectPlacesByTitleContaining(placeListReqDTO, pageable);
        return new Result(resDtos);
    }

    @GetMapping("/tourlist/location")
    public Result tourListByLocation(Pageable pageable, @RequestParam("areacode") String areaCode) {
        List<PlaceListResDTO> resDtos = placeListService.selectByLocationPlaceList(areaCode, pageable);
        return new Result(resDtos);
    }

    @GetMapping("/tourlist/hashtag")
    public Result tourListByLocation(Pageable pageable, @RequestParam("hashtag") List<String> hashtag) {
        List<PlaceListResDTO> resDtos = placeListService.selectByTagPlaceList(hashtag, pageable);
        return new Result(resDtos);
    }

    @GetMapping("/tourlist")
    public Result mainPlace(Pageable pageable) {
        List<PlaceListResDTO> placeListResDTOS = placeListService.mainPlaceTourlist(pageable);
        return new Result(placeListResDTOS);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

}
