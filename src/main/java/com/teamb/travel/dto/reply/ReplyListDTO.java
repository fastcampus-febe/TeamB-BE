package com.teamb.travel.dto.reply;

import com.teamb.travel.entity.Reply;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReplyListDTO {

    private Long reviewId;
    private String memberId;
    private LocalDate visitDate;
    private String weather;
    private Double rate;
    private String review;

    public ReplyListDTO(Reply reply) {
        this.reviewId = reply.getReviewId();
        this.memberId = reply.getMemberId();
        this.visitDate = reply.getVisitDate();
        this.weather = reply.getWeather();
        this.rate = reply.getRate();
        this.review = reply.getReview();
    }
}

