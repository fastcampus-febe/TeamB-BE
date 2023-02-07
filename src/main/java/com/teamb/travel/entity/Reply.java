package com.teamb.travel.entity;

import com.teamb.travel.dto.reply.ReplyModifiedReqDTO;
import com.teamb.travel.dto.reply.ReplyRequestDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Reply {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private String memberId;
    private String password;
    private LocalDate visitDate;
    private String weather;
    private Double rate;
    private String review;
    @Column(name = "contentid")
    private String placeDetailContentid;

    public Double isRate() {
        if (rate == null) {
            return 0.0;
        } else {
            return rate;
        }
    }

    public Reply(ReplyRequestDTO replyRequestDTO) {
        this.memberId = replyRequestDTO.getMemberId();
        this.password = replyRequestDTO.getPassword();
        this.visitDate = replyRequestDTO.getVisitDate();
        this.weather = replyRequestDTO.getWeather();
        this.rate = replyRequestDTO.getRate();
        this.review = replyRequestDTO.getContent();
        this.placeDetailContentid = replyRequestDTO.getContentid();
    }

    public void replyModified(ReplyModifiedReqDTO replyModifiedReqDTO) {
        review = replyModifiedReqDTO.getReview();
        visitDate = replyModifiedReqDTO.getVisitDate();
        weather = replyModifiedReqDTO.getWeather();
        rate = replyModifiedReqDTO.getRate();
    }
}