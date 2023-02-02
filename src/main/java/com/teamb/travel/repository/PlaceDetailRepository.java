package com.teamb.travel.repository;

import com.teamb.travel.entity.Place;
import com.teamb.travel.entity.PlaceDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceDetailRepository extends JpaRepository<PlaceDetail, Long> {

    public PlaceDetail findByContentid(String contentid);
}
