package com.teamb.travel.repository;

import com.teamb.travel.entity.IsIndoor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IsIndoorRepository extends JpaRepository<IsIndoor, Long> {
    @Query(value = "select * from is_indoor a where left(a.mapx, 7) = (:mapX) AND left(a.mapy, 6) = (:mapY)", nativeQuery = true)
    IsIndoor findAllByMapXAndMapY(@Param("mapX") String mapX, @Param("mapY") String mapY);

}
