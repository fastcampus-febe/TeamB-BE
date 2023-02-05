package com.teamb.travel.dto.place.detailMaker;

import com.teamb.travel.entity.Place;
import lombok.Data;

@Data
public class PlaceMaker {
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
    // place
    private String firstimage;

    public PlaceMaker(Place place) {
        addr1 = place.getAddr1();
        contentid = place.getContentid();
        firstimage2 = place.getFirstimage2();
        title = place.getTitle();
        mapy = place.getMapy();
        mapx = place.getMapx();
        cat3 = place.getCat3();
        firstimage = place.getFirstimage();

    }
}