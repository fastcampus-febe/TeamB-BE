package com.teamb.travel.repository;

import com.teamb.travel.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    public List<Reply> findReplysByPlaceDetailContentid(String contentid);

    @Query(value = "select round(avg(r.rate), 1) from reply r where r.contentid IN (:contentid)", nativeQuery = true)
    Double findByPlaceDetailInNativeQuery(@Param("contentid") String contentid);
}
