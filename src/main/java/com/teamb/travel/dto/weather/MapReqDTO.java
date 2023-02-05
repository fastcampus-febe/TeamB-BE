package com.teamb.travel.dto.weather;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MapReqDTO {
    private String mapX;
    private String mapY;
}
