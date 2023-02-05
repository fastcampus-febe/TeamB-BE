package com.teamb.travel.service.weather;

import com.teamb.travel.api.weather.MiddleLastWeatherDetailApi;
import com.teamb.travel.api.weather.ShortWeatherApi;
import com.teamb.travel.api.weather.TempApi;
import com.teamb.travel.dto.WeatherLastResDTO;
import com.teamb.travel.dto.WeatherShortMiddleResDTO;
import com.teamb.travel.dto.WeatherResDTO;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final ShortWeatherService shortWeatherService;
    private final MiddleWeatherService middleWeatherService;
    private final LastWeatherService lastWeatherService;

    // 각 Service에서 가져온 단기, 중기, 장기 날씨 정보를 하나의 DTO(WeatherResDTO)로 묶어줌
    public WeatherResDTO findWeatherResDTOByMapXAndMapY (String mapX, String mapY) throws IOException, ParseException {
        List<WeatherShortMiddleResDTO> shortRes = shortWeatherService.selectShortWeather(mapX, mapY);
        List<WeatherShortMiddleResDTO> middleRes = middleWeatherService.selectMiddleWeather(mapX, mapY);
        List<WeatherLastResDTO> lastRes = lastWeatherService.selectLastWeather(mapX, mapY);

        WeatherResDTO weatherResDto = new WeatherResDTO(shortRes, middleRes, lastRes);
        return weatherResDto;
    }

}
