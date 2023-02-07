package com.teamb.travel.controller;

import com.teamb.travel.dto.reply.*;
import com.teamb.travel.service.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Api(tags = {"댓글 관련 API"}, description = "댓글 CRUD API")
@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/tourlist/review/update")
    public Result findReplyByReviewId(@RequestBody ReplyUpdateReqDTO replyUpdateReqDTO) {

        ReplyUpdateResDTO replyUpdateResDTO = replyService.findReplyByReviewId(replyUpdateReqDTO.getReviewId());
        return new Result(replyUpdateResDTO);
    }

    @ApiOperation(value = "리뷰 삭제/수정을 위한 본인 확인", notes = "리뷰 삭제/조회을 위한 본인 확인 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reviewId", value = "리뷰의 작성 번호" ,required = true ),
            @ApiImplicitParam(name = "password", value = "리뷰를 작성한 멤버의 password" ,required = true )
    })
    @PostMapping("/tourlist/review/check")
    public ResponseEntity<String> replyCheck(@RequestBody ReplyCheckReqDTO replyCheckReqDto) {
        String message = replyService.findByReviewIdAndPassword(replyCheckReqDto);

        if (message.equals("success")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "리뷰 검색", notes = "contentid에 쓰여져 있는 리뷰를 검색한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contentid", value = "리뷰가 써있는지 확인할 contentid" ,required = true )
    })
    @GetMapping("/tourlist/review")
    public Result replyListForPlaceDetailPage(@RequestParam("contentid") String contentid) {
        return new Result(replyService.findByContentid(contentid));

    }

    @ApiOperation(value = "리뷰 생성", notes = "리뷰를 새로 작성하는 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "리뷰를 작성한 멤버의 id" ,required = true ),
            @ApiImplicitParam(name = "password", value = "리뷰를 작성한 멤버의 password" ,required = true ),
            @ApiImplicitParam(name = "placeTitle", value = "관광지의 placeTitle" ,required = true ),
            @ApiImplicitParam(name = "contentid", value = "리뷰가 작성된 관광지의 contentid" ,required = true ),
            @ApiImplicitParam(name = "visitDate", value = "리뷰를 작성한 사람의 방문날짜" ,required = true ),
            @ApiImplicitParam(name = "weather", value = "리뷰를 작성한 사람의 방문시 날씨" ,required = true ),
            @ApiImplicitParam(name = "rate", value = "리뷰를 작성한 사람의 관광지에 대한 평점" ,required = true ),
            @ApiImplicitParam(name = "content", value = "리뷰내용" ,required = true )
    })
    @PostMapping("/tourlist/review/insert")
    public ResponseEntity<String> replySave(@RequestBody ReplyRequestDTO replyRequestDTO) {
        String savedReply = replyService.save(replyRequestDTO);
        ResponseEntity responseEntity = new ResponseEntity(savedReply, HttpStatus.OK);
        return responseEntity;
    }

    @ApiOperation(value = "리뷰 삭제", notes = "리뷰를 삭제하는 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reviewId", value = "리뷰의 작성 번호" ,required = true )
    })
    @DeleteMapping("/tourlist/review")
    public ResponseEntity<String> replyDelete(@RequestBody ReplyUpdateReqDTO replyUpdateReqDTO) {
        String deleteReply = replyService.deleteReply(replyUpdateReqDTO);
        ResponseEntity responseEntity = new ResponseEntity(deleteReply, HttpStatus.OK);
        return responseEntity;
    }

    @ApiOperation(value = "리뷰 수정 버튼", notes = "리뷰를 수정하는 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reviewId", value = "리뷰의 작성 번호" ,required = true ),
            @ApiImplicitParam(name = "visitDate", value = "리뷰를 작성한 사람의 방문날짜" ,required = true ),
            @ApiImplicitParam(name = "weather", value = "리뷰를 작성한 사람의 방문시 날씨" ,required = true ),
            @ApiImplicitParam(name = "rate", value = "리뷰를 작성한 사람의 관광지에 대한 평점" ,required = true ),
            @ApiImplicitParam(name = "review", value = "리뷰내용" ,required = true )
    })
    @PutMapping("/tourlist/review/update")
    public ResponseEntity<String> replyModify(@RequestBody ReplyModifiedReqDTO replyModifiedReqDTO) {
        String modified = replyService.modified(replyModifiedReqDTO);
        ResponseEntity responseEntity = new ResponseEntity(modified, HttpStatus.OK);
        return responseEntity;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

}
