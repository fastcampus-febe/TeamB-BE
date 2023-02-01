package com.teamb.travel.dto;

import com.teamb.travel.entity.IsIndoor;
import com.teamb.travel.entity.Place;
import com.teamb.travel.entity.PlaceDetail;
import com.teamb.travel.entity.Reply;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PlaceByTagResDto {
    private String addr1;
    private String contentid;
    private String title;
    private String cat3;
    private String infocenter;
    private String firstimage;
    private String inOut;
    private Double rate;

    public PlaceByTagResDto (Place place, PlaceDetail placeDetail, IsIndoor indoor, Reply reply) {
        this.addr1 = place.getAddr1();
        this.contentid = place.getContentid();
        this.title = place.getTitle();
        this.cat3 = place.getCat3();
        this.infocenter = placeDetail.getInfocenter();
        this.firstimage = place.getFirstimage();
        this.inOut = indoor.getInOut();
        this.rate = reply.getRate();
    }
}
