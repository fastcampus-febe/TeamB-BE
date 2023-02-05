package com.teamb.travel.api.weather;

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

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;

        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();

        String result = sb.toString();

        JSONParser parser = new JSONParser(); // Json parser를 만들어 만들어진 문자열 데이터를 객체화
        JSONObject obj = (JSONObject) parser.parse(result);
        JSONObject parse_response = (JSONObject) obj.get("response"); // response 키를 가지고 데이터를 파싱
        JSONObject parse_body = (JSONObject) parse_response.get("body"); // response 로 부터 body 찾기
        JSONObject parse_items = (JSONObject) parse_body.get("items"); // body 로 부터 items 찾기
        JSONArray parse_item = (JSONArray) parse_items.get("item"); // items로 부터 itemlist 를 받기
        return (JSONObject) parse_item.get(0);
    }

}
