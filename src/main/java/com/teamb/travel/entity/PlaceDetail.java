package com.teamb.travel.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class PlaceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contentid;
    private String title;
    private String telname;
    private String homepage;
    private String overview;
    private String parking;
    private String expagerange;
    private String chkpet;
    private String infocenter;
    private String restdate;
    private String opendate;
    private String accomcount;
    private String usetime;
    private String heritage1;
    private String heritage2;
    private String heritage3;
    private String userseason;
    private String chkbabycarriage;
    private String expguide;
    private String chkcreditcard;

    @Builder
    public PlaceDetail(String contentid, String title, String telname, String homepage, String overview, String parking,
                       String expagerange, String chkpet, String infocenter, String restdate, String opendate, String accomcount,
                       String usetime, String heritage1, String heritage2, String heritage3, String userseason, String chkbabycarriage,
                       String expguide, String chkcreditcard) {
        this.contentid = contentid;
        this.title = title;
        this.telname = telname;
        this.homepage = homepage;
        this.overview = overview;
        this.parking = parking;
        this.expagerange = expagerange;
        this.chkpet = chkpet;
        this.infocenter = infocenter;
        this.restdate = restdate;
        this.opendate = opendate;
        this.accomcount = accomcount;
        this.usetime = usetime;
        this.heritage1 = heritage1;
        this.heritage2 = heritage2;
        this.heritage3 = heritage3;
        this.userseason = userseason;
        this.chkbabycarriage = chkbabycarriage;
        this.expguide = expguide;
        this.chkcreditcard = chkcreditcard;
    }
}
