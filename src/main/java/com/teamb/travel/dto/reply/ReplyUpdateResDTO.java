package com.teamb.travel.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReplyUpdateResDTO {

    private String memberId;
    private LocalDate visitDate;
    private String weather;
    private Double rate;
    private String review;
}
