//package com.teamb.travel.api;
//
//import com.teamb.travel.entity.Place;
//import com.teamb.travel.repository.PlaceRepository;
//import lombok.RequiredArgsConstructor;
//import org.json.JSONObject;
//import org.json.XML;
//import org.json.simple.JSONArray;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//
//@Component
//@RequiredArgsConstructor
//public class PlaceApi {
//    private final PlaceRepository repository;
//
//    public void tourCheck() throws IOException, ParseException {
//        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService/areaBasedList"); /*URL*/
//        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
//        urlBuilder.append("?" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("11972", "UTF-8")); /*한 페이지 결과 수*/
//        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
//        urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*XML 또는 JSON*/
//        urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*날짜*/
//        urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + "51%2Bc6%2BNBpG7JFhyeZbKn4BDMVtvgE6W15oUad4G2n74%2Bv7Bo4oHKoQL%2FwLSaBnD67u%2F5CorapB5I6WMBLpXEkg%3D%3D"); /*Service Key*/
//        urlBuilder.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
//        urlBuilder.append("&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("A", "UTF-8")); /*지역*/
//        urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8")); /*지역*/
//        urlBuilder.append("&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*지역*/
//        urlBuilder.append("&" + URLEncoder.encode("sigunguCode", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*지역*/
//        urlBuilder.append("&" + URLEncoder.encode("cat1", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*지역*/
//        urlBuilder.append("&" + URLEncoder.encode("cat2", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*지역*/
//        urlBuilder.append("&" + URLEncoder.encode("cat3", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*지역*/
//        // 3. URL 객체 생성.
//        URL url = new URL(urlBuilder.toString());
//        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        // 5. 통신을 위한 메소드 SET.
//        conn.setRequestMethod("GET");
//        // 6. 통신을 위한 Content-type SET.
//        conn.setRequestProperty("Content-type", "application/json");
//        // 7. 통신 응답 코드 확인.
//        System.out.println("Response code: " + conn.getResponseCode());
//        // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
//        BufferedReader rd;
//        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        } else {
//            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//        }
//        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//        // 10. 객체 해제.
//        rd.close();
//        conn.disconnect();
//
//        System.out.println(urlBuilder);
//        // 11. 전달받은 데이터 확인.
//        JSONObject json = XML.toJSONObject(String.valueOf(sb));
//        String jsonStr = json.toString(4);
//        System.out.println(jsonStr);
//
//        JSONParser parser = new JSONParser(); // Json parser를 만들어 만들어진 문자열 데이터를 객체화
//        org.json.simple.JSONObject obj = (org.json.simple.JSONObject) parser.parse(jsonStr);
//        org.json.simple.JSONObject parse_response = (org.json.simple.JSONObject) obj.get("response"); // response 키를 가지고 데이터를 파싱
//        org.json.simple.JSONObject parse_body = (org.json.simple.JSONObject) parse_response.get("body"); // response 로 부터 body 찾기
//        org.json.simple.JSONObject parse_items = (org.json.simple.JSONObject) parse_body.get("items"); // body 로 부터 items 찾기
//        JSONArray parse_item = (JSONArray) parse_items.get("item"); // items로 부터 itemlist 를 받기
//
//        ArrayList<Place> placeArrayList = new ArrayList<>();
//
//        for (int i = 0; i < parse_item.size(); i++) {
//            org.json.simple.JSONObject keyObj = (org.json.simple.JSONObject) parse_item.get(i);
//            Place place = new Place(
//                    String.valueOf(keyObj.get("addr1")),
//                    String.valueOf(keyObj.get("addr2")),
//                    String.valueOf(keyObj.get("areacode")),
//                    String.valueOf(keyObj.get("booktour")),
//                    String.valueOf(keyObj.get("cat1")),
//                    String.valueOf(keyObj.get("cat2")),
//                    String.valueOf(keyObj.get("cat3")),
//                    String.valueOf(keyObj.get("contentid")),
//                    String.valueOf(keyObj.get("contenttypeid")),
//                    String.valueOf(keyObj.get("createdtime")),
//                    String.valueOf(keyObj.get("firstimage")),
//                    String.valueOf(keyObj.get("firstimage2")),
//                    String.valueOf(keyObj.get("mapx")),
//                    String.valueOf(keyObj.get("mapy")),
//                    String.valueOf(keyObj.get("mlevel")),
//                    String.valueOf(keyObj.get("modifiedtime")),
//                    String.valueOf(keyObj.get("readcount")),
//                    String.valueOf(keyObj.get("sigungucode")),
//                    String.valueOf(keyObj.get("tel")),
//                    String.valueOf(keyObj.get("title")),
//                    String.valueOf(keyObj.get("zipcode"))
//            );
//            placeArrayList.add(place);
//        }
//
//        System.out.println(placeArrayList);
//
//        for (int i = 0; i < placeArrayList.size(); i++) {
//            repository.save(placeArrayList.get(i));
//        }
//    }
//
//}
