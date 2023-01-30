package com.teamb.travel.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IsIndoor {
    @Id
    private Long placeId;

    private String mapX;
    private String mapY;
    private String placeName;
    private String inOut;

}

