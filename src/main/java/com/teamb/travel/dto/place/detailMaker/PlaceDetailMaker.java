package com.teamb.travel.dto.detailMaker;

import com.teamb.travel.entity.PlaceDetail;
import lombok.Data;

@Data
public class PlaceDetailMaker {
    //PlaceDetail
    private String overview;
    //PlaceDetail
    private String homepage;
    //PlaceDetail
    private String parking;
    //PlaceDetail
    private String expagerange;
    //PlaceDetail
    private String chkpet;
    //PlaceDetail
    private String infocenter;
    //PlaceDetail
    private String restdate;
    //PlaceDetail
    private String opendate;
    //PlaceDetail
    private String accomcount;
    //PlaceDetail
    private String usetime;
    //PlaceDetail
    private String heritage3;
    //PlaceDetail
    private String heritage2;
    //PlaceDetail
    private String heritage1;
    //PlaceDetail
    private String useseason;
    //PlaceDetail
    private String chkbabycarriage;
    //PlaceDetail
    private String expguide;
    //PlaceDetail
    private String chkcreditcard;

    public PlaceDetailMaker(PlaceDetail placeDetail) {
        overview = placeDetail.getOverview();
        homepage = placeDetail.homepageTokenizer();
        parking = placeDetail.getParking();
        expagerange = placeDetail.getExpagerange();
        chkpet = placeDetail.getChkpet();
        infocenter = placeDetail.getInfocenter();
        restdate = placeDetail.getRestdate();
        opendate = placeDetail.getOpendate();
        accomcount = placeDetail.getAccomcount();
        usetime = placeDetail.getUsetime();
        heritage3 = placeDetail.getHeritage3();
        heritage2 = placeDetail.getHeritage2();
        heritage1 = placeDetail.getHeritage1();
        useseason = placeDetail.getUserseason();
        chkbabycarriage = placeDetail.getChkbabycarriage();
        expguide = placeDetail.getExpguide();
        chkcreditcard = placeDetail.getChkcreditcard();
    }
}