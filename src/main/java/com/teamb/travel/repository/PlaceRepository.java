package com.teamb.travel.repository;

import com.teamb.travel.entity.Place;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query(value = "select p.contentid from place p", nativeQuery = true)
    public List<String> findByAllContentid();

    public List<Place> findAllByAreacodeOrderByIdAsc(String areaCode, Pageable pageable);

    public List<Place> findAllByCat3InOrderByIdAsc(List<String> hashtag, Pageable pageable);

    // where title like '%title%' : 해당 title이 포함된 Place들을 반환
    public List<Place> findPlacesByTitleContaining(String title, Pageable pageable);

    Optional<Place> findPlaceByContentid(String contentid);
}
