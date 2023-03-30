package com.teamb.travel.repository;

import com.teamb.travel.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findReplysByPlaceDetailContentid(String contentid);

    Reply findReplyByReviewId(long reviewId);

    @Query(value = "select ROUND(AVG(r.rate), 1) from Reply r where r.placeDetailContentid IN (:contentid)")
    Double findByPlaceDetailInNativeQuery(@Param("contentid") String contentid);

    Reply findByReviewIdAndPassword(Long reviewId, String password);

}
