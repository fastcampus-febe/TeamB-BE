package com.teamb.travel.service;

import com.teamb.travel.dto.PlaceDetailResDTO;
import com.teamb.travel.dto.detailMaker.EtcMaker;
import com.teamb.travel.dto.detailMaker.PlaceDetailMaker;
import com.teamb.travel.dto.detailMaker.PlaceMaker;
import com.teamb.travel.entity.IsIndoor;
import com.teamb.travel.entity.Place;
import com.teamb.travel.entity.PlaceDetail;
import com.teamb.travel.entity.Reply;
import com.teamb.travel.repository.IsIndoorRepository;
import com.teamb.travel.repository.PlaceDetailRepository;
import com.teamb.travel.repository.PlaceRepository;
import com.teamb.travel.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        Reply findReply = replyRepository.findByRate(contenid);
        IsIndoor findInOut = isIndoorRepository.findByMapXAndMapYInout(placeMaker.getMapx(), placeMaker.getMapy());
        EtcMaker etcMaker = new EtcMaker(findReply, findInOut);

        return new PlaceDetailResDTO(placeMaker, placeDetailMaker, etcMaker);
    }
}
