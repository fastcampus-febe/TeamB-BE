package com.teamb.travel.controller;

import com.teamb.travel.dto.MapReqDTO;
import com.teamb.travel.dto.WeatherResDTO;
import com.teamb.travel.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/tourlist/weather")
    public ResponseEntity<WeatherResDTO> findWeather(@RequestBody MapReqDTO mapReqDto) throws IOException, ParseException {
        return new ResponseEntity<>(weatherService.weatherResDtos(mapReqDto.getMapX(), mapReqDto.getMapY()), HttpStatus.OK);
    }
}
