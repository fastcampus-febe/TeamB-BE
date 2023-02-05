package com.teamb.travel.dto.reply;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReplyRequestDTO {
    private String memberId;
    private String password;
    private String placeTitle;
    private String contentid;
    private LocalDate visitDate;
    private String weather;
    private Double rate;
    private String content;
}
