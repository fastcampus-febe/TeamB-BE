package com.teamb.travel.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class WeatherResDTO {
    private List<WeatherShortMiddleResDTO> weatherShortResDTOs;
    private List<WeatherShortMiddleResDTO> weatherMiddleResDTOs;
    private List<WeatherLastResDTO> weatherLastResDTOs;
}
