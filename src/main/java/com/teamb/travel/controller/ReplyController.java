package com.teamb.travel.controller;

import com.teamb.travel.dto.PlaceListResDTO;
import com.teamb.travel.dto.ReplyUpdateReqDTO;
import com.teamb.travel.dto.ReplyUpdateResDTO;
import com.teamb.travel.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @PostMapping("/tourlist/review/update")
    public ResponseEntity<ReplyUpdateResDTO> findReplyByReviewId(@RequestBody ReplyUpdateReqDTO replyUpdateReqDTO) {

        ReplyUpdateResDTO replyUpdateResDTO = replyService.findReplyByReviewId(replyUpdateReqDTO.getReviewId());
        return new ResponseEntity<>(replyUpdateResDTO, HttpStatus.OK);
    }
}
