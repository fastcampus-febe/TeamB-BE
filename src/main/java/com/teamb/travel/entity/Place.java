package com.teamb.travel.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Place {
    @Id
    private String contentid;
    private Long id;
    private String addr1;
    private String addr2;
    private String areacode;
    private String booktour;
    private String cat1;
    private String cat2;
    private String cat3;
    private String contenttypeid;
    private String createdtime;
    private String firstimage;
    private String firstimage2;
    private String mapx;
    private String mapy;
    private String mlevel;
    private String modifiedtime;
    private String readcount;
    private String sigungucode;
    private String tel;
    private String title;
    private String zipcode;
}
