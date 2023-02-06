package com.teamb.travel.api;

import com.teamb.travel.entity.Place;
import com.teamb.travel.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class PlaceApi {

    private final PlaceRepository repository;
    private final UrlBuilderToJSONArray urlBuilderToJSONArray;

    public void tourCheck() throws IOException, ParseException {

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService/areaBasedList"); /*URL*/
        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
        urlBuilder.append("?" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("11972", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*XML 또는 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*날짜*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + "51%2Bc6%2BNBpG7JFhyeZbKn4BDMVtvgE6W15oUad4G2n74%2Bv7Bo4oHKoQL%2FwLSaBnD67u%2F5CorapB5I6WMBLpXEkg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
        urlBuilder.append("&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("A", "UTF-8")); /*지역*/
        urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8")); /*지역*/
        urlBuilder.append("&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*지역*/
        urlBuilder.append("&" + URLEncoder.encode("sigunguCode", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*지역*/
        urlBuilder.append("&" + URLEncoder.encode("cat1", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*지역*/
        urlBuilder.append("&" + URLEncoder.encode("cat2", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*지역*/
        urlBuilder.append("&" + URLEncoder.encode("cat3", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*지역*/

        JSONArray parse_item = urlBuilderToJSONArray.urlBuilderToJSONArray(urlBuilder, DataType.XML);

        ArrayList<Place> placeArrayList = new ArrayList<>();

        for (int i = 0; i < parse_item.size(); i++) {
            org.json.simple.JSONObject keyObj = (org.json.simple.JSONObject) parse_item.get(i);
            Place place = new Place(
                    String.valueOf(keyObj.get("addr1")),
                    String.valueOf(keyObj.get("addr2")),
                    String.valueOf(keyObj.get("areacode")),
                    String.valueOf(keyObj.get("booktour")),
                    String.valueOf(keyObj.get("cat1")),
                    String.valueOf(keyObj.get("cat2")),
                    String.valueOf(keyObj.get("cat3")),
                    String.valueOf(keyObj.get("contentid")),
                    String.valueOf(keyObj.get("contenttypeid")),
                    String.valueOf(keyObj.get("createdtime")),
                    String.valueOf(keyObj.get("firstimage")),
                    String.valueOf(keyObj.get("firstimage2")),
                    String.valueOf(keyObj.get("mapx")),
                    String.valueOf(keyObj.get("mapy")),
                    String.valueOf(keyObj.get("mlevel")),
                    String.valueOf(keyObj.get("modifiedtime")),
                    String.valueOf(keyObj.get("readcount")),
                    String.valueOf(keyObj.get("sigungucode")),
                    String.valueOf(keyObj.get("tel")),
                    String.valueOf(keyObj.get("title")),
                    String.valueOf(keyObj.get("zipcode"))
            );
            placeArrayList.add(place);
        }

        System.out.println(placeArrayList);

        for (int i = 0; i < placeArrayList.size(); i++) {
            repository.save(placeArrayList.get(i));
        }
    }

}
