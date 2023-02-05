package com.teamb.travel.dto.reply;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReplyModifiedReqDTO {
    private Long reviewId;
    private LocalDate visitDate;
    private String weather;
    private Double rate;
    private String review;
}
