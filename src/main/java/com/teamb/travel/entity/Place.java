package com.teamb.travel.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
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

    public Place(String addr1, String addr2, String areacode, String booktour,
                 String cat1, String cat2, String cat3, String contentid,
                 String contenttypeid, String createdtime, String firstimage,
                 String firstimage2, String mapx, String mapy, String mlevel,
                 String modifiedtime, String readcount, String sigungucode,
                 String tel, String title, String zipcode) {
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.areacode = areacode;
        this.booktour = booktour;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.cat3 = cat3;
        this.contentid = contentid;
        this.contenttypeid = contenttypeid;
        this.createdtime = createdtime;
        this.firstimage = firstimage;
        this.firstimage2 = firstimage2;
        this.mapx = mapx;
        this.mapy = mapy;
        this.mlevel = mlevel;
        this.modifiedtime = modifiedtime;
        this.readcount = readcount;
        this.sigungucode = sigungucode;
        this.tel = tel;
        this.title = title;
        this.zipcode = zipcode;
    }
}