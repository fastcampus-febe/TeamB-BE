package com.teamb.travel.dto.place;

import com.teamb.travel.dto.place.detailMaker.EtcMaker;
import com.teamb.travel.dto.place.detailMaker.PlaceDetailMaker;
import com.teamb.travel.dto.place.detailMaker.PlaceMaker;
import lombok.Data;

@Data
public class PlaceDetailResDTO {
    //PlaceDetail
    private String overview;
    // place
    private String addr1;
    // place
    private String contentid;
    // place
    private String firstimage2;
    // place
    private String title;
    // place
    private String mapy;
    // place
    private String mapx;
    // place
    private String cat3;
    //PlaceDetail
    private String homepage;
    // place
    private String firstimage;
    //reply
    private Double rate;
    //is_indoor
    private String inOut;
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

    public PlaceDetailResDTO(PlaceMaker pm, PlaceDetailMaker pdm, EtcMaker etc) {
        overview = pdm.getOverview();
        addr1 = pm.getAddr1();
        contentid = pm.getContentid();
        firstimage2 = pm.getFirstimage2();
        title = pm.getTitle();
        mapy = pm.getMapy();
        mapx = pm.getMapx();
        cat3 = pm.getCat3();
        homepage = pdm.getHomepage();
        firstimage = pm.getFirstimage();
        rate = etc.getRate();
        inOut = etc.getInOut();
        parking = pdm.getParking();
        expagerange = pdm.getExpagerange();
        chkpet = pdm.getChkpet();
        infocenter = pdm.getInfocenter();
        restdate = pdm.getRestdate();
        opendate = pdm.getOpendate();
        accomcount = pdm.getAccomcount();
        usetime = pdm.getUsetime();
        heritage3 = pdm.getHeritage3();
        heritage2 = pdm.getHeritage2();
        heritage1 = pdm.getHeritage1();
        useseason = pdm.getUseseason();
        chkbabycarriage = pdm.getChkbabycarriage();
        expguide = pdm.getExpguide();
        chkcreditcard = pdm.getChkcreditcard();
    }
}