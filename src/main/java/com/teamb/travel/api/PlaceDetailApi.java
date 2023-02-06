package com.teamb.travel.api;

import com.teamb.travel.entity.PlaceDetail;
import com.teamb.travel.repository.PlaceDetailRepository;
import com.teamb.travel.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaceDetailApi {
    @Value("${api.serviceKey}")
    private String serviceKey;

    private final PlaceDetailRepository placeDetailRepository;
    private final PlaceRepository placeRepository;
    private final UrlBuilderToJSONArray urlBuilderToJSONArray;

    int count = 9915;
    private int NUMBER = count;

//    @PostConstruct
    public void tourDetailTestCheck() throws IOException, ParseException, org.json.simple.parser.ParseException {
        List<String> contentIds = placeRepository.findByAllContentid();
        System.out.println(contentIds.size());
        try {

            for (int i = NUMBER; i < contentIds.size(); i++) {
                StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService/detailCommon"); /*URL*/
                NUMBER = i;
                // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
                urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
                urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8")); /*지역*/
                urlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(contentIds.get(i), "UTF-8")); /*지역*/
                urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*XML 또는 JSON*/
                urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*날짜*/
                urlBuilder.append("&" + URLEncoder.encode("defaultYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
                urlBuilder.append("&" + URLEncoder.encode("firstImageYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
                urlBuilder.append("&" + URLEncoder.encode("areacodeYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
                urlBuilder.append("&" + URLEncoder.encode("catcodeYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
                urlBuilder.append("&" + URLEncoder.encode("addrinfoYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
                urlBuilder.append("&" + URLEncoder.encode("mapinfoYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/
                urlBuilder.append("&" + URLEncoder.encode("overviewYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역*/


                StringBuilder urlBuilder2 = new StringBuilder("http://apis.data.go.kr/B551011/KorService/detailIntro"); /*URL*/
                // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
                urlBuilder2.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + "51%2Bc6%2BNBpG7JFhyeZbKn4BDMVtvgE6W15oUad4G2n74%2Bv7Bo4oHKoQL%2FwLSaBnD67u%2F5CorapB5I6WMBLpXEkg%3D%3D"); /*Service Key*/
                urlBuilder2.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8")); /*지역*/
                urlBuilder2.append("&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(contentIds.get(i), "UTF-8")); /*지역*/
                urlBuilder2.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*XML 또는 JSON*/
                urlBuilder2.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*날짜*/


                JSONObject parse_item = (JSONObject) urlBuilderToJSONArray.urlBuilderToJSONArray(urlBuilder, DataType.XML).get(0);
//                org.json.simple.JSONObject parse_item = (org.json.simple.JSONObject) parse_items.get("item"); // items로 부터 itemlist 를 받기

                JSONObject parse_item2 = (JSONObject) urlBuilderToJSONArray.urlBuilderToJSONArray(urlBuilder2, DataType.XML).get(0);
//                org.json.simple.JSONObject parse_item2 = (org.json.simple.JSONObject) parse_items2.get("item"); // items로 부터 itemlist 를 받기

                PlaceDetail placeDetail = new PlaceDetail(String.valueOf(parse_item.get("contentid")),
                        String.valueOf(parse_item.get("title")),
                        String.valueOf(parse_item.get("telname")),
                        String.valueOf(parse_item.get("homepage")),
                        String.valueOf(parse_item.get("overview")),
                        String.valueOf(parse_item2.get("parking")),
                        String.valueOf(parse_item2.get("expagerange")),
                        String.valueOf(parse_item2.get("chkpet")),
                        String.valueOf(parse_item2.get("infocenter")),
                        String.valueOf(parse_item2.get("restdate")),
                        String.valueOf(parse_item2.get("opendate")),
                        String.valueOf(parse_item2.get("accomcount")),
                        String.valueOf(parse_item2.get("usetime")),
                        String.valueOf(parse_item2.get("heritage1")),
                        String.valueOf(parse_item2.get("heritage2")),
                        String.valueOf(parse_item2.get("heritage3")),
                        String.valueOf(parse_item2.get("userseason")),
                        String.valueOf(parse_item2.get("chkbabycarriage")),
                        String.valueOf(parse_item2.get("expguide")),
                        String.valueOf(parse_item2.get("chkcreditcard")));

                placeDetailRepository.save(placeDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(NUMBER);
        }
    }
}