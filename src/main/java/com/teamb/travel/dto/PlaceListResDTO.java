package com.teamb.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlaceListResDTO {

    private String addr1;
    private String contentid;
    private String title;
    private String cat3;
    private String firstimage;
    private String infocenter; // place_detail 테이블
    private Double rate; // reply 테이블
//    private String inOut; // is_indoor 테이블
}
