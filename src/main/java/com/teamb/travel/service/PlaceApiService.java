package com.teamb.travel.service;

import com.teamb.travel.api.PlaceApi;
import com.teamb.travel.entity.Place;
import com.teamb.travel.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PlaceApiService {

    private final PlaceApi placeApi;
    private final PlaceRepository placeRepository;

    public void savePlacesFromPlaceApi() throws IOException, ParseException {
        JSONArray placesJSONArray = placeApi.placesJSONArray();

        for (int i = 0; i < placesJSONArray.size(); i++) {
            // JSONArray에서 JSONObject를 하나씩 가져오면서
            JSONObject placeJSONObject = (JSONObject) placesJSONArray.get(i);

            // Place로 만들고,
            Place place = new Place(
                    String.valueOf(placeJSONObject.get("addr1")),
                    String.valueOf(placeJSONObject.get("addr2")),
                    String.valueOf(placeJSONObject.get("areacode")),
                    String.valueOf(placeJSONObject.get("booktour")),
                    String.valueOf(placeJSONObject.get("cat1")),
                    String.valueOf(placeJSONObject.get("cat2")),
                    String.valueOf(placeJSONObject.get("cat3")),
                    String.valueOf(placeJSONObject.get("contentid")),
                    String.valueOf(placeJSONObject.get("contenttypeid")),
                    String.valueOf(placeJSONObject.get("createdtime")),
                    String.valueOf(placeJSONObject.get("firstimage")),
                    String.valueOf(placeJSONObject.get("firstimage2")),
                    String.valueOf(placeJSONObject.get("mapx")),
                    String.valueOf(placeJSONObject.get("mapy")),
                    String.valueOf(placeJSONObject.get("mlevel")),
                    String.valueOf(placeJSONObject.get("modifiedtime")),
                    String.valueOf(placeJSONObject.get("readcount")),
                    String.valueOf(placeJSONObject.get("sigungucode")),
                    String.valueOf(placeJSONObject.get("tel")),
                    String.valueOf(placeJSONObject.get("title")),
                    String.valueOf(placeJSONObject.get("zipcode"))
            );

            // 그 Place를 DB에 저장함
            placeRepository.save(place);
        }
    }
}
