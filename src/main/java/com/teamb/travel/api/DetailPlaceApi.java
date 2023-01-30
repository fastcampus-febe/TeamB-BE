//package com.teamb.travel.api;
//
//import com.teamb.travel.entity.PlaceDetail;
//import com.teamb.travel.repository.PlaceDetailRepository;
//import com.teamb.travel.repository.PlaceRepository;
//import lombok.RequiredArgsConstructor;
//import org.json.JSONObject;
//import org.json.XML;
//import org.json.simple.parser.JSONParser;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class DetailPlaceApi {
//    private final PlaceDetailRepository placeDetailRepository;
//    private final PlaceRepository placeRepository;
//    int count = 9915;
//    private  int NUMBER = count;
////614
//    //2794
//
//    @PostConstruct
//    public void tourDetailTestCheck() throws IOException, ParseException, org.json.simple.parser.ParseException {
//        List<String> contentIds = placeRepository.findByAllContentid();
//        System.out.println(contentIds.size());
//        try {
//
//            for (int i = NUMBER; i < contentIds.size(); i++) {
//                StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService/detailCommon"); /*URL*/
//                NUMBER = i;
//                // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
//                urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + "51%2Bc6%2BNBpG7JFhyeZbKn4BDMVtvgE6W15oUad4G2n74%2Bv7Bo4oHKoQL%2FwLSaBnD67u%2F5CorapB5I6WMBLpXEkg%3D%3D"); /*Service Key*/
//                urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8")); /*지역*/
//                urlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(contentIds.get(i), "UTF-8")); /*지역*/
//                urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*XML 또는 JSON*/
//                urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*날짜*/
//                urlBuilder.append("&" + URLEncoder.encode("defaultYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
//                urlBuilder.append("&" + URLEncoder.encode("firstImageYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
//                urlBuilder.append("&" + URLEncoder.encode("areacodeYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
//                urlBuilder.append("&" + URLEncoder.encode("catcodeYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
//                urlBuilder.append("&" + URLEncoder.encode("addrinfoYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
//                urlBuilder.append("&" + URLEncoder.encode("mapinfoYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
//                urlBuilder.append("&" + URLEncoder.encode("overviewYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
//                // 3. URL 객체 생성.
//                URL url = new URL(urlBuilder.toString());
//                // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                // 5. 통신을 위한 메소드 SET.
//                conn.setRequestMethod("GET");
//                // 6. 통신을 위한 Content-type SET.
//                conn.setRequestProperty("Content-type", "application/json");
//                // 7. 통신 응답 코드 확인.
////            System.out.println("Response code: " + conn.getResponseCode());
//                // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
//                BufferedReader rd;
//                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                } else {
//                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//                }
//                // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
//                StringBuilder sb = new StringBuilder();
//                String line;
//                while ((line = rd.readLine()) != null) {
//                    sb.append(line);
//                }
//                // 10. 객체 해제.
//                rd.close();
//                conn.disconnect();
//
//                // 11. 전달받은 데이터 확인.
//                JSONObject json = XML.toJSONObject(String.valueOf(sb));
//                String jsonStr = json.toString(4);
//
//                StringBuilder urlBuilder2 = new StringBuilder("http://apis.data.go.kr/B551011/KorService/detailIntro"); /*URL*/
//                // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
//                urlBuilder2.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + "51%2Bc6%2BNBpG7JFhyeZbKn4BDMVtvgE6W15oUad4G2n74%2Bv7Bo4oHKoQL%2FwLSaBnD67u%2F5CorapB5I6WMBLpXEkg%3D%3D"); /*Service Key*/
//                urlBuilder2.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8")); /*지역*/
//                urlBuilder2.append("&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(contentIds.get(i), "UTF-8")); /*지역*/
//                urlBuilder2.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*XML 또는 JSON*/
//                urlBuilder2.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*날짜*/
//                // 3. URL 객체 생성.
//                URL url2 = new URL(urlBuilder2.toString());
//                // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
//                HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
//                // 5. 통신을 위한 메소드 SET.
//                conn2.setRequestMethod("GET");
//                // 6. 통신을 위한 Content-type SET.
//                conn2.setRequestProperty("Content-type", "application/json");
//                // 7. 통신 응답 코드 확인.
////            System.out.println("Response code: " + conn2.getResponseCode());
//                // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
//                BufferedReader rd2;
//                if (conn2.getResponseCode() >= 200 && conn2.getResponseCode() <= 300) {
//                    rd2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
//                } else {
//                    rd2 = new BufferedReader(new InputStreamReader(conn2.getErrorStream()));
//                }
//                // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
//                StringBuilder sb2 = new StringBuilder();
//                String line2;
//                while ((line2 = rd2.readLine()) != null) {
//                    sb2.append(line2);
//                }
//                // 10. 객체 해제.
//                rd2.close();
//                conn2.disconnect();
//
//                // 11. 전달받은 데이터 확인.
//                JSONObject json2 = XML.toJSONObject(String.valueOf(sb2));
//                String jsonStr2 = json2.toString(4);
//
//                JSONParser parser = new JSONParser(); // Json parser를 만들어 만들어진 문자열 데이터를 객체화
//                org.json.simple.JSONObject obj = (org.json.simple.JSONObject) parser.parse(jsonStr);
//                org.json.simple.JSONObject parse_response = (org.json.simple.JSONObject) obj.get("response"); // response 키를 가지고 데이터를 파싱
//                org.json.simple.JSONObject parse_body = (org.json.simple.JSONObject) parse_response.get("body"); // response 로 부터 body 찾기
//                org.json.simple.JSONObject parse_items = (org.json.simple.JSONObject) parse_body.get("items"); // body 로 부터 items 찾기
//                org.json.simple.JSONObject parse_item = (org.json.simple.JSONObject) parse_items.get("item"); // items로 부터 itemlist 를 받기
//
//                JSONParser parser2 = new JSONParser(); // Json parser를 만들어 만들어진 문자열 데이터를 객체화
//                org.json.simple.JSONObject obj2 = (org.json.simple.JSONObject) parser2.parse(jsonStr2);
//                org.json.simple.JSONObject parse_response2 = (org.json.simple.JSONObject) obj2.get("response"); // response 키를 가지고 데이터를 파싱
//                org.json.simple.JSONObject parse_body2 = (org.json.simple.JSONObject) parse_response2.get("body"); // response 로 부터 body 찾기
//                org.json.simple.JSONObject parse_items2 = (org.json.simple.JSONObject) parse_body2.get("items"); // body 로 부터 items 찾기
//                org.json.simple.JSONObject parse_item2 = (org.json.simple.JSONObject) parse_items2.get("item"); // items로 부터 itemlist 를 받기
//
//                PlaceDetail placeDetail = new PlaceDetail(String.valueOf(parse_item.get("contentid")),
//                        String.valueOf(parse_item.get("title")),
//                        String.valueOf(parse_item.get("telname")),
//                        String.valueOf(parse_item.get("homepage")),
//                        String.valueOf(parse_item.get("overview")),
//                        String.valueOf(parse_item2.get("parking")),
//                        String.valueOf(parse_item2.get("expagerange")),
//                        String.valueOf(parse_item2.get("chkpet")),
//                        String.valueOf(parse_item2.get("infocenter")),
//                        String.valueOf(parse_item2.get("restdate")),
//                        String.valueOf(parse_item2.get("opendate")),
//                        String.valueOf(parse_item2.get("accomcount")),
//                        String.valueOf(parse_item2.get("usetime")),
//                        String.valueOf(parse_item2.get("heritage1")),
//                        String.valueOf(parse_item2.get("heritage2")),
//                        String.valueOf(parse_item2.get("heritage3")),
//                        String.valueOf(parse_item2.get("userseason")),
//                        String.valueOf(parse_item2.get("chkbabycarriage")),
//                        String.valueOf(parse_item2.get("expguide")),
//                        String.valueOf(parse_item2.get("chkcreditcard")));
//
//                placeDetailRepository.save(placeDetail);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(NUMBER);
//        }
//    }
//}