package com.teamb.travel.service.weather;

import com.teamb.travel.api.weather.MiddleLastWeatherDetailApi;
import com.teamb.travel.api.weather.TempApi;
import com.teamb.travel.dto.weather.WeatherShortMiddleResDTO;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MiddleWeatherService {

    // 중기 날씨 조회하는 Service.
    // api에서 조회해온 중기 날씨 정보를 WeatherShortMiddleResDTO로 반환함.

    private final MiddleLastWeatherDetailApi middleLastWeatherDetailApi;
    private final TempApi tempApi;

    public List<WeatherShortMiddleResDTO> selectMiddleWeather(String mapX, String mapY) throws IOException, ParseException {
        List<WeatherShortMiddleResDTO> resDtos = new ArrayList<>();

        LocalDateTime date = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();

        int result = 3;

        if (date.getHour() < 6) {
            localDate = LocalDate.now().minusDays(1);
            result = 4;
        }

        JSONObject keyObj = middleLastWeatherDetailApi.selectWeatherDetail(mapX, mapY);
        JSONObject keyObjTemp = tempApi.selectTemp(mapX, mapY);

        for (int i = result; i < 8; i++) {
            LocalDate dateList = localDate.plusDays(i);
            String rainAm = String.valueOf(keyObj.get("rnSt" + i + "Am"));
            String rainPm = String.valueOf(keyObj.get("rnSt" + i + "Pm"));
            String weatherAm = String.valueOf(keyObj.get("wf" + i + "Am"));
            String weatherPm = String.valueOf(keyObj.get("wf" + i + "Pm"));
            String lowTemp = String.valueOf(keyObjTemp.get("taMin" + i));
            String highTemp = String.valueOf(keyObjTemp.get("taMax" + i));

            WeatherShortMiddleResDTO dto = WeatherShortMiddleResDTO.builder()
                    .localDate(dateList).rainProbabilityAm(rainAm)
                    .rainProbabilityPm(rainPm).weatherAm(weatherAm)
                    .weatherPm(weatherPm).lowTemp(lowTemp).highTemp(highTemp).build();

            resDtos.add(dto);
        }
        return resDtos;
    }
}
