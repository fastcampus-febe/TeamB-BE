package com.teamb.travel.service;

import com.teamb.travel.api.PlaceDetailApi;
import com.teamb.travel.entity.PlaceDetail;
import com.teamb.travel.repository.PlaceDetailRepository;
import com.teamb.travel.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceDetailApiService {

    private final PlaceDetailApi placeDetailApi;
    private final PlaceRepository placeRepository;
    private final PlaceDetailRepository placeDetailRepository;

//    @PostConstruct
    public void savePlaceDetailsByContentIdList() {
        int number = 0;
        List<String> contentIds = placeRepository.findByAllContentid();
        try {
            for (int i = 0; i < contentIds.size(); i++) {
                number = i;
                savePlaceDetailFromPlaceDetailApiByContentId(contentIds.get(i));
            }

        // IOException, ParseException 및 NullPointerException(api 호출 가능 최대 횟수 초과 시 발생) 발생 가능함.
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("오류가 발생한 number : " + number + "/ contentId : " + contentIds.get(number));
        }
    }

    public void savePlaceDetailFromPlaceDetailApiByContentId(String contentId) throws Exception {

        // 1. api를 호출하여 가져온 JSONArray로 JSONObject를 만들고,
        JSONObject commonJSONObject = (JSONObject) placeDetailApi.placeDetailCommonJSONArrayByContentId(contentId).get(0);
        JSONObject introJSONObject = (JSONObject) placeDetailApi.placeDetailIntroJSONArrayByContentId(contentId).get(0);

        // 2. PlaceDetail로 만들고,
        PlaceDetail placeDetail = new PlaceDetail(
                String.valueOf(commonJSONObject.get("contentid")),
                String.valueOf(commonJSONObject.get("title")),
                String.valueOf(commonJSONObject.get("telname")),
                String.valueOf(commonJSONObject.get("homepage")),
                String.valueOf(commonJSONObject.get("overview")),
                String.valueOf(introJSONObject.get("parking")),
                String.valueOf(introJSONObject.get("expagerange")),
                String.valueOf(introJSONObject.get("chkpet")),
                String.valueOf(introJSONObject.get("infocenter")),
                String.valueOf(introJSONObject.get("restdate")),
                String.valueOf(introJSONObject.get("opendate")),
                String.valueOf(introJSONObject.get("accomcount")),
                String.valueOf(introJSONObject.get("usetime")),
                String.valueOf(introJSONObject.get("heritage1")),
                String.valueOf(introJSONObject.get("heritage2")),
                String.valueOf(introJSONObject.get("heritage3")),
                String.valueOf(introJSONObject.get("userseason")),
                String.valueOf(introJSONObject.get("chkbabycarriage")),
                String.valueOf(introJSONObject.get("expguide")),
                String.valueOf(introJSONObject.get("chkcreditcard"))
        );

        // 3. 그 PlaceDetail을 DB에 저장함
        placeDetailRepository.save(placeDetail);
    }

}
