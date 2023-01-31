package com.teamb.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name = "contentid", referencedColumnName = "contentid")
    private PlaceDetail placeDetail;

}
