package com.teamb.travel.controller;

import com.teamb.travel.dto.ReplyCheckReqDto;
import com.teamb.travel.dto.ReplyRequestDTO;
import com.teamb.travel.dto.ReplyUpdateReqDTO;
import com.teamb.travel.dto.ReplyUpdateResDTO;
import com.teamb.travel.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @PostMapping("/tourlist/review/update")
    public Result findReplyByReviewId(@RequestBody ReplyUpdateReqDTO replyUpdateReqDTO) {

        ReplyUpdateResDTO replyUpdateResDTO = replyService.findReplyByReviewId(replyUpdateReqDTO.getReviewId());
        return new Result(replyUpdateResDTO);
    }

    @PostMapping("/tourlist/review/check")
    public ResponseEntity<String> replyCheck(@RequestBody ReplyCheckReqDto replyCheckReqDto) {
        String message = replyService.findByReviewIdAndPassword(replyCheckReqDto);

        if (message.equals("success")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tourlist/review")
    public Result replyListForPlaceDetailPage(@RequestParam("contentid") String contentid) {
        return new Result(replyService.findByContentid(contentid));

    }

    @PostMapping("/tourlist/review/insert")
    public ResponseEntity<String> replySave(@RequestBody ReplyRequestDTO replyRequestDTO) {
        String savedReply = replyService.save(replyRequestDTO);
        ResponseEntity responseEntity = new ResponseEntity(savedReply, HttpStatus.OK);
        return responseEntity;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

}
