package com.teamb.travel.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentid")
    private PlaceDetail placeDetail;

}
