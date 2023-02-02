package com.teamb.travel.service;

import com.teamb.travel.dto.ReplyUpdateResDTO;
import com.teamb.travel.entity.Reply;
import com.teamb.travel.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyUpdateResDTO findReplyByReviewId(Long reviewId) {
        Reply reply = replyRepository.findReplyByReviewId(reviewId);

        return new ReplyUpdateResDTO(reply.getMemberId(), reply.getVisitDate(), reply.getWeather(),
                reply.getRate(), reply.getReview());
    }
}
