package com.teamb.travel.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class WeatherMiddleResDto {
    private LocalDate localDate;
    private String weatherAm;
    private String weatherPm;
    private String rainProbabilityAm;
    private String rainProbabilityPm;
    private String lowTemp;
    private String highTemp;
}
