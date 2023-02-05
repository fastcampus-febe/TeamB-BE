package com.teamb.travel.controller;

import com.teamb.travel.dto.place.PlaceDetailResDTO;
import com.teamb.travel.service.PlaceDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlaceDetailController {

    private final PlaceDetailService placeDetailService;

    @GetMapping("/tourlist/detail")
    public PlaceDetailResDTO placeDetailRes(@RequestParam("contentid") String contentid) {
        return placeDetailService.getPlaceDetail(contentid);
    }
}