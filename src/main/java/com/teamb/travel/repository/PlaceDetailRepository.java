package com.teamb.travel.repository;

import com.teamb.travel.entity.PlaceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceDetailRepository extends JpaRepository<PlaceDetail,Long> {
}
