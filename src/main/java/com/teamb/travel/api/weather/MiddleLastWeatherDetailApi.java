package com.teamb.travel.api.weather;

import com.teamb.travel.api.UrlBuilderToJSONArray;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MiddleLastWeatherDetailApi {

    // 중기, 장기 날씨 상세정보를 조회하는 외부 api에서 정보를 가져와 JSONObject로 반환함

    private final FindLocationCodeByMapXAndMapY findLocationCode;
    private final UrlBuilderToJSONArray urlBuilderToJSONArray;

    public JSONObject selectWeatherDetail(String mapX, String mapY) throws IOException, ParseException {
        LocalDateTime date = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();

        if (date.getHour() < 18) {
            localDate = LocalDate.now().minusDays(1);
        }

        String locationCode = findLocationCode.findLocation(mapX, mapY);
        String now = localDate.toString().replace("-", "");

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + "51%2Bc6%2BNBpG7JFhyeZbKn4BDMVtvgE6W15oUad4G2n74%2Bv7Bo4oHKoQL%2FwLSaBnD67u%2F5CorapB5I6WMBLpXEkg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*XML 또는 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("tmFc", "UTF-8") + "=" + URLEncoder.encode(now + "1800", "UTF-8")); /*날짜*/
        urlBuilder.append("&" + URLEncoder.encode("regId", "UTF-8") + "=" + URLEncoder.encode(locationCode, "UTF-8")); /*지역*/

        JSONArray parse_item = urlBuilderToJSONArray.urlBuilderToJSONArray(urlBuilder);
        return (JSONObject) parse_item.get(0);
    }

}
