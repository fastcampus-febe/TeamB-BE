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
public class TempApi {
    @Value("${api.serviceKey}")
    private String serviceKey;

    // 기온 정보를 제공하는 외부 api에서 정보를 가져와 JSONObject로 반환함

    private final FindLocationCodeByMapXAndMapY findLocationCode;
    private final UrlBuilderToJSONArray urlBuilderToJSONArray;

    public JSONObject selectTemp (String mapX, String mapY) throws IOException, ParseException, NullPointerException {
        LocalDateTime date = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();

        if (date.getHour() < 6) {
            localDate = LocalDate.now().minusDays(1);
        }

        String locationCode = findLocationCode.findLocation(mapX, mapY);
        String now = localDate.toString().replace("-", "");

        String locationTemp = findLocationForTemperature(locationCode);

        StringBuilder urlBuilderTemp = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa"); /*URL*/
        urlBuilderTemp.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilderTemp.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*XML 또는 JSON*/
        urlBuilderTemp.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilderTemp.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilderTemp.append("&" + URLEncoder.encode("tmFc", "UTF-8") + "=" + URLEncoder.encode(now + "0600", "UTF-8")); /*날짜*/
        urlBuilderTemp.append("&" + URLEncoder.encode("regId", "UTF-8") + "=" + URLEncoder.encode(locationTemp, "UTF-8")); /*지역*/

        JSONArray parse_itemTemp = urlBuilderToJSONArray.urlBuilderToJSONArray(urlBuilderTemp, DataType.JSON);
        return (JSONObject) parse_itemTemp.get(0);
    }

    public String findLocationForTemperature(String code) {
        if (code.equals("11B00000")) {
            return "11B10101";
        } else if (code.equals("11D10000")) {
            return "11D10502";
        } else if (code.equals("11D20000")) {
            return "11D20501";
        } else if (code.equals("11C20000")) {
            return "11C20401";
        } else if (code.equals("11C10000")) {
            return "11C10301";
        } else if (code.equals("11F20000")) {
            return "11F20501";
        } else if (code.equals("11F10000")) {
            return "11F10201";
        } else if (code.equals("11H10000")) {
            return "11H10701";
        } else if (code.equals("11H20000")) {
            return "11H20201";
        } else if (code.equals("11G00000")) {
            return "11G00201";
        }
        return null;
    }

}
