package com.teamb.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

}
