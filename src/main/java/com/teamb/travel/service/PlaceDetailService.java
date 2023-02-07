package com.teamb.travel.service;

import com.teamb.travel.dto.place.PlaceDetailResDTO;
import com.teamb.travel.dto.place.detailMaker.EtcMaker;
import com.teamb.travel.dto.place.detailMaker.PlaceDetailMaker;
import com.teamb.travel.dto.place.detailMaker.PlaceMaker;
import com.teamb.travel.entity.IsIndoor;
import com.teamb.travel.entity.Place;
import com.teamb.travel.entity.PlaceDetail;
import com.teamb.travel.repository.IsIndoorRepository;
import com.teamb.travel.repository.PlaceDetailRepository;
import com.teamb.travel.repository.PlaceRepository;
import com.teamb.travel.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceDetailService {

    private final PlaceDetailRepository placeDetailRepository;
    private final PlaceRepository placeRepository;
    private final ReplyRepository replyRepository;
    private final IsIndoorRepository isIndoorRepository;

    public PlaceDetailResDTO getPlaceDetail(String contenid) {
        // 플레이스를 먼저 가져온다
        Optional<Place> findPlace = placeRepository.findPlaceByContentid(contenid);
        Place place = findPlace.orElseThrow(() -> new IllegalStateException("잘못된 contentid입니다."));
        PlaceMaker placeMaker = new PlaceMaker(place);

        Optional<PlaceDetail> findPlaceDetail = placeDetailRepository.findByContentid(contenid);
        PlaceDetail placeDetail = findPlaceDetail.orElseThrow(() -> new IllegalStateException("잘못된 contenid입니다."));
        PlaceDetailMaker placeDetailMaker = new PlaceDetailMaker(placeDetail);

        Double placeDetailPageRate = replyRepository.findByPlaceDetailInNativeQuery(contenid);
        List<IsIndoor> findInOuts = isIndoorRepository.findAllByMapXAndMapY(placeMaker.getMapx(), placeMaker.getMapy());
        if (findInOuts.size() == 0) {
            findInOuts.add(new IsIndoor());
        }
        EtcMaker etcMaker = new EtcMaker(placeDetailPageRate, findInOuts.get(0));

        return new PlaceDetailResDTO(placeMaker, placeDetailMaker, etcMaker);
    }
}