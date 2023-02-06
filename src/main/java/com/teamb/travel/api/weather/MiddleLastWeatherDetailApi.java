package com.teamb.travel.api.weather;

import com.teamb.travel.api.DataType;
import com.teamb.travel.api.UrlBuilderToJSONArray;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MiddleLastWeatherDetailApi {
    @Value("${api.serviceKey}")
    private String serviceKey;

    // 중기, 장기 날씨 상세정보를 조회하는 외부 api에서 정보를 가져와 JSONObject로 반환함

    private final FindLocationCodeByMapXAndMapY findLocationCode;
    private final UrlBuilderToJSONArray urlBuilderToJSONArray;

    public JSONObject selectWeatherDetail(String mapX, String mapY) throws IOException, ParseException {
        LocalDateTime date = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();

        if (date.getHour() < 6) {
            localDate = LocalDate.now().minusDays(1);
        }

        String locationCode = findLocationCode.findLocation(mapX, mapY);
        String now = localDate.toString().replace("-", "");

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*XML 또는 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("tmFc", "UTF-8") + "=" + URLEncoder.encode(now + "0600", "UTF-8")); /*날짜*/
        urlBuilder.append("&" + URLEncoder.encode("regId", "UTF-8") + "=" + URLEncoder.encode(locationCode, "UTF-8")); /*지역*/

        JSONArray parse_item = urlBuilderToJSONArray.urlBuilderToJSONArray(urlBuilder, DataType.JSON);
        return (JSONObject) parse_item.get(0);
    }

}
