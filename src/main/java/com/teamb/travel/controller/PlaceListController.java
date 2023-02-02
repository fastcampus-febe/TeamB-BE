package com.teamb.travel.controller;

import com.teamb.travel.dto.PlaceListReqDTO;
import com.teamb.travel.dto.PlaceListResDTO;
import com.teamb.travel.service.PlaceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlaceListController {

    @Autowired
    PlaceListService placeListService;

    @GetMapping("/tourlist/keyword")
    public List<PlaceListResDTO> keywordPlacesSearch(@RequestParam String keyword, Pageable pageable) {

        PlaceListReqDTO placeListReqDTO = new PlaceListReqDTO(keyword);
        return placeListService.selectPlacesByTitleContaining(placeListReqDTO, pageable);
    }

    @GetMapping("/tourlist/location")
    public ResponseEntity<List<PlaceListResDTO>> tourListByLocation(Pageable pageable, @RequestParam("areacode") String areaCode) {
        List<PlaceListResDTO> resDtos = placeListService.selectByLocationPlaceList(areaCode, pageable);
        return new ResponseEntity<>(resDtos, HttpStatus.OK);
    }

    @GetMapping("/tourlist/hashtag")
    public ResponseEntity<List<PlaceListResDTO>> tourListByLocation(Pageable pageable, @RequestParam("hashtag") List<String> hashtag) {
        List<PlaceListResDTO> resDtos = placeListService.selectByTagPlaceList(hashtag, pageable);
        return new ResponseEntity<>(resDtos, HttpStatus.OK);
    }
}
