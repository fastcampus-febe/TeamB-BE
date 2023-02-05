package com.teamb.travel.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class WeatherLastResDTO {
    private LocalDate localDate;
    private String weather;
    private String rainProbability;
    private String lowTemp;
    private String highTemp;
}
