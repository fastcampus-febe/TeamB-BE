package com.teamb.travel.dto;

import com.teamb.travel.entity.Place;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PlaceListReqDTO {

    private String title;

    public PlaceListReqDTO(String title) {
        this.title = title;
    }

    public Place toEntity() {
        return Place.builder()
                .title(this.title)
                .build();
    }
}
