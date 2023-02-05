package com.teamb.travel.service.weather;

import com.teamb.travel.api.weather.ShortWeatherApi;
import com.teamb.travel.dto.WeatherShortMiddleResDTO;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShortWeatherService {

    // 단기 날씨 조회하는 Service.
    // api에서 조회해온 단기 날씨 정보를 WeatherShortMiddleResDTO로 반환함.

    private final ShortWeatherApi shortWeatherApi;

    public List<WeatherShortMiddleResDTO> selectShortWeather(String mapX, String mapY) throws IOException, ParseException {
        List<WeatherShortMiddleResDTO> shortResDTOs = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            JSONArray shortJSONArray = shortWeatherApi.selectShortWeatherJSON(mapX, mapY, i);

            // JSONArray(shortJSONArray)에서 필요한 값을 찾기 위해, index로 쓰일 값을 초기화함
            int eightAmPOPRainNum = 0;

            // 필요한 값(오전 8시의 강수확률(POP) = eightAmPOPRainNum)을 찾기 위해서
            // for문(반복문)을 돌며 JSONArray(shortJSONArray)에서 해당 index 값을 찾음
            for (int k=0; k<shortJSONArray.size(); k++) {
                JSONObject jsonObject = (JSONObject)shortJSONArray.get(k);

                if (jsonObject.get("category").equals("POP") && jsonObject.get("fcstTime").equals("0800")) {
                    eightAmPOPRainNum = k;
                    break;
                }
            }

            // 사용하는 날씨 api (https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15084084) 에서
            // eightAmPOPRainNum(오전 8시의 강수확률(POP) 데이터가 있는 index 값)을 알면 나머지 필요한 정보의 index 값도 알 수 있으므로
            // 위에서 찾은 eightAmPOPRainNum로 나머지 index 값도 구함.
            int fivePmPOPRainNum = eightAmPOPRainNum+109;
            int eightAmSKYWeatherNum = eightAmPOPRainNum-2;
            int fivePmSKYWeatherNum = eightAmPOPRainNum+107;
            int lowTempTMNNum = eightAmPOPRainNum-20;
            int highTempTMXNum = eightAmPOPRainNum+89;

            // WeatherShortMiddleResDTO로 만들기 위한 작업
            // 위에서 찾은 index 값으로 데이터를 가져온다
            LocalDate date = LocalDate.now().plusDays(i-1);
            String rainAm = getFcstValueByJSONArrayIndex(shortJSONArray, eightAmPOPRainNum);
            String rainPm = getFcstValueByJSONArrayIndex(shortJSONArray, fivePmPOPRainNum);

            String weatherAmBeforeConvert = getFcstValueByJSONArrayIndex(shortJSONArray, eightAmSKYWeatherNum);
            String weatherAm = convertWeatherToStringByWeatherNum(weatherAmBeforeConvert);
            String weatherPmBeforeConvert = getFcstValueByJSONArrayIndex(shortJSONArray, fivePmSKYWeatherNum);
            String weatherPm = convertWeatherToStringByWeatherNum(weatherPmBeforeConvert);

            String lowTemp = getFcstValueByJSONArrayIndex(shortJSONArray, lowTempTMNNum);
            String highTemp = getFcstValueByJSONArrayIndex(shortJSONArray, highTempTMXNum);

            // WeatherShortMiddleResDTO 생성
            WeatherShortMiddleResDTO shortResDTO = WeatherShortMiddleResDTO.builder()
                    .localDate(date).rainProbabilityAm(rainAm)
                    .rainProbabilityPm(rainPm).weatherAm(weatherAm)
                    .weatherPm(weatherPm).lowTemp(lowTemp).highTemp(highTemp).build();

            // List<WeatherShortMiddleResDTO> shortResDTOs에 WeatherShortMiddleResDTO를 추가하고
            shortResDTOs.add(shortResDTO);
        }

        // 그 List (List<WeatherShortMiddleResDTO> shortResDTOs) 를 반환
        return shortResDTOs;
    }

    public String getFcstValueByJSONArrayIndex (JSONArray jsonArray, int jsonIndex) {
        JSONObject jsonObject = (JSONObject)jsonArray.get(jsonIndex);
        return String.valueOf(jsonObject.get("fcstValue"));
    }

    public String convertWeatherToStringByWeatherNum (String stringWeatherNum) {
        double weatherNum = Double.parseDouble(stringWeatherNum);
        String weatherString = "";

        if ( weatherNum < 6 ) {
            weatherString = "맑음";
        } else if ( weatherNum < 9 ) {
            weatherString = "구름많음";
        } else if (weatherNum < 11) {
            weatherString = "흐림";
        } else {
            weatherString = "?";
        }

        return weatherString;
    }
}
