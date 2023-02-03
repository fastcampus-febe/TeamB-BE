package com.teamb.travel.repository;

import com.teamb.travel.dto.ReplyCheckReqDto;
import com.teamb.travel.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    public List<Reply> findReplysByPlaceDetailContentid(String contentid);

    public Reply findReplyByReviewId(long reviewId);

    @Query(value = "select round(avg(r.rate), 1) from reply r where r.contentid IN (:contentid)", nativeQuery = true)
    Double findByPlaceDetailInNativeQuery(@Param("contentid") String contentid);

    Reply findByReviewIdAndPassword(Long reviewId, String password);

    @Query(value = "select r.rate from Reply r where r.placeDetailContentid = :contentid")
    Reply findByRate(@Param("contentid") String contentid);

}
