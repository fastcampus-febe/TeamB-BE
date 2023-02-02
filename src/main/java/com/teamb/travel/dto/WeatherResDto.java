package com.teamb.travel.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class WeatherResDto {
    private List<WeatherMiddleResDto> weatherMiddleResDto;
    private List<WeatherLastResDto> weatherLastResDto;
}
