package com.teamb.travel.entity;

import lombok.*;

import javax.persistence.*;
import java.util.StringTokenizer;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class PlaceDetail{
    private Long id;
    @Id
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


    public String homepageTokenizer() {
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(homepage, "\"");
        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            if (s.startsWith("https:")) {
                sb.append(s);
            }
            if (s.startsWith("http:")) {
                sb.append(s);
            }
        }
        return sb.toString();
    }
}