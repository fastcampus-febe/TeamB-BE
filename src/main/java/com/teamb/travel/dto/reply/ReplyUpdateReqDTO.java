package com.teamb.travel.dto;

import com.teamb.travel.entity.Place;
import com.teamb.travel.entity.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReplyUpdateReqDTO {

    private Long reviewId;

    public ReplyUpdateReqDTO(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Reply toEntity() {
        return Reply.builder()
                .reviewId(this.reviewId)
                .build();
    }
}
