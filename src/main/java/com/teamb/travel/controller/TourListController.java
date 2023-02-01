package com.teamb.travel.controller;


import com.teamb.travel.dto.PlaceByLocationResDto;
import com.teamb.travel.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TourListController {

    private final PlaceService placeService;

    @GetMapping("/tourlist/location")
    public ResponseEntity<List<PlaceByLocationResDto>> tourListByLocation(@RequestParam("pageno") int pageNo, @RequestParam("areacode") String areaCode) {
        List<PlaceByLocationResDto> resDtos = placeService.selectByLocationPlaceList(areaCode, pageNo);
        return new ResponseEntity<>(resDtos, HttpStatus.OK);
    }
}
