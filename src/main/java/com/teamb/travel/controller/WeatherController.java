package com.teamb.travel.controller;

import com.teamb.travel.dto.weather.WeatherResDTO;
import com.teamb.travel.service.weather.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(tags = {"날씨 관련 API"}, description = "날씨 조회 API")
@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @ApiOperation(value = "날씨 검색", notes = "요청 일을 기준으로 +10일 까지의 날씨 데이터를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mapX", value = "X좌표"),
            @ApiImplicitParam(name = "mapY", value = "Y좌표")
    })
    @GetMapping("/tourlist/weather")
    public Result findWeather(@RequestParam("mapX") String mapX,@RequestParam("mapY") String mapY) throws IOException, ParseException {
        WeatherResDTO weatherResDTO = weatherService.findWeatherResDTOByMapXAndMapY(mapX, mapY);
        return new Result(weatherResDTO);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
