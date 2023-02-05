package com.teamb.travel.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class IsIndoor {
    @Id
    private String placeId;
    private String mapX;
    private String mapY;
    private String placeName;
    private String inOut;

}