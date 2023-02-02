package com.teamb.travel.dto;

import com.teamb.travel.entity.Reply;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReplyCheckReqDto {
    private Long reviewId;
    private String password;

    public Reply toEntity() {
        return Reply.builder().reviewId(reviewId).password(password).build();
    }
}
