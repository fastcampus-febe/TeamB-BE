package com.teamb.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IsIndoor {
    @Id
    private String placeId;
    private String mapX;
    private String mapY;
    private String placeName;
    private String inOut;

}

