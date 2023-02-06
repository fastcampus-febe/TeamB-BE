package com.teamb.travel.api;

import org.json.XML;
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

@Component
public class UrlBuilderToJSONArray {

    // urlBuilder(StringBuilder)를 매개변수로 받아
    // response - body - items 안에 있는 item List를 JSONArray로 반환하는 메서드
    public JSONArray urlBuilderToJSONArray (StringBuilder urlBuilder, String dataType) throws IOException, ParseException {

        // 1. urlBuilder(StringBuilder)로부터 URL 객체 생성.
        URL url = new URL(urlBuilder.toString());

        // 2. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 3. 통신을 위한 메소드 SET.
        conn.setRequestMethod("GET");

        // 4. 통신을 위한 Content-type SET.
        conn.setRequestProperty("Content-type", "application/json");

        // 5. 통신 응답 코드 확인.
//        System.out.println("Response code: " + conn.getResponseCode());

        // 6. 전달받은 데이터를 BufferedReader 객체로 저장.
        BufferedReader rd;

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        // 7. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        // 8. 객체 해제.
        rd.close();
        conn.disconnect();

        // 9. 전달받은 데이터(result는 api로부터 받아온 값), dataType에 따라 변환 방법이 다름
        String result = "";
        if (dataType.equalsIgnoreCase("JSON")) {
            result = sb.toString();
        } else if (dataType.equalsIgnoreCase("XML")) {
            org.json.JSONObject json = XML.toJSONObject(String.valueOf(sb));
            result = json.toString(4);
        }
//        System.out.println("result : " + result);

        // 10. Json parser를 만들어 만들어진 문자열 데이터를 객체화
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(result);

        JSONObject parse_response = (JSONObject) obj.get("response"); // response 키를 가지고 데이터를 파싱
        JSONObject parse_body = (JSONObject) parse_response.get("body"); // response 로 부터 body 찾기
        JSONObject parse_items = (JSONObject) parse_body.get("items"); // body 로 부터 items 찾기
        JSONArray parse_item = (JSONArray) parse_items.get("item"); // items로 부터 itemList 를 받기
        return parse_item;
    }
}
