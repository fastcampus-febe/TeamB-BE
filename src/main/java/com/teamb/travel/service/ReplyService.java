package com.teamb.travel.service;

import com.teamb.travel.dto.ReplyCheckReqDto;
import com.teamb.travel.dto.ReplyListDTO;
import com.teamb.travel.dto.ReplyRequestDTO;
import com.teamb.travel.dto.ReplyUpdateResDTO;
import com.teamb.travel.entity.Reply;
import com.teamb.travel.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyUpdateResDTO findReplyByReviewId(Long reviewId) {
        Reply reply = replyRepository.findReplyByReviewId(reviewId);

        return new ReplyUpdateResDTO(reply.getMemberId(), reply.getVisitDate(), reply.getWeather(),
                reply.getRate(), reply.getReview());
    }

    public String findByReviewIdAndPassword(ReplyCheckReqDto dto) {
        try {
            Reply replyBefore = dto.toEntity();
            Reply replyAfter = replyRepository.findByReviewIdAndPassword(dto.getReviewId(), dto.getPassword());
            if (replyBefore.getReviewId().equals(replyAfter.getReviewId()) && replyBefore.getPassword().equals(replyAfter.getPassword())) {
                return "success";
            } else {
                return "failed";
            }
        } catch (NullPointerException e) {
            return "failed";
        }
    }

    public List<ReplyListDTO> findByContentid(String contentid) {
        List<Reply> findReplies = replyRepository.findReplysByPlaceDetailContentid(contentid);
        List<ReplyListDTO> result = findReplies.stream().map(reply -> new ReplyListDTO(reply)).collect(Collectors.toList());
        return result;
    }

    public String save(ReplyRequestDTO replyRequestDTO) {
        Reply reply = new Reply(replyRequestDTO);
        replyRepository.save(reply);
        return "success";
    }
}
