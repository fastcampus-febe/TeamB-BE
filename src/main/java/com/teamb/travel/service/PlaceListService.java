package com.teamb.travel.service;

import com.teamb.travel.dto.PlaceListReqDTO;
import com.teamb.travel.dto.PlaceListResDTO;
import com.teamb.travel.entity.IsIndoor;
import com.teamb.travel.entity.Place;
import com.teamb.travel.entity.PlaceDetail;
import com.teamb.travel.entity.Reply;
import com.teamb.travel.repository.IsIndoorRepository;
import com.teamb.travel.repository.PlaceDetailRepository;
import com.teamb.travel.repository.PlaceRepository;
import com.teamb.travel.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceListService {

    private final PlaceRepository repository;
    private final PlaceDetailRepository placeDetailRepository;
    private final ReplyRepository replyRepository;
    private final IsIndoorRepository indoorRepository;

    public void saveEntity(Place place) {
        repository.save(place);
    }

    // placeList를 매개변수로 받으면 거기에 맞는 관광지 List를 반환하는 메서드
    private List<PlaceListResDTO> PlacesListByPlaceList(List<Place> placeList) {

        List<PlaceDetail> details = new ArrayList<>();
        List<Double> rates = new ArrayList<>();
        List<String> inOuts = new ArrayList<>();

        for ( Place place : placeList) {
            String contentid = place.getContentid();

            details.add(placeDetailRepository.findByContentid(contentid));

            Double avgRate = replyRepository.findByPlaceDetailInNativeQuery(contentid);
            if (avgRate == null) {
                rates.add(0.0);
            } else {
                rates.add(avgRate);
            }

            IsIndoor indoors = indoorRepository.findAllByMapXAndMapY(place.getMapx().substring(0, 7), place.getMapy().substring(0, 6));
            if (indoors == null) {
                inOuts.add("");
            } else {
                inOuts.add(indoors.getInOut());
            }

        }

        List<PlaceListResDTO> placeListResDTOS = new ArrayList<>();
        for (int i = 0; i < placeList.size(); i++) {
            placeListResDTOS.add(new PlaceListResDTO(placeList.get(i).getAddr1(), placeList.get(i).getContentid(),
                    placeList.get(i).getTitle(), placeList.get(i).getCat3(), placeList.get(i).getFirstimage(),
                    details.get(i).getInfocenter(), rates.get(i), inOuts.get(i)));
        }

        return placeListResDTOS;
    }

    public List<PlaceListResDTO> selectPlacesByTitleContaining(PlaceListReqDTO placeListReqDTO, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 12);

        List<Place> places = repository.findPlacesByTitleContaining(placeListReqDTO.getTitle(), pageable);

        return PlacesListByPlaceList(places);
    }

    public List<PlaceListResDTO> selectByLocationPlaceList(String areaCode, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 12);

        List<Place> places = repository.findAllByAreacodeOrderByIdAsc(areaCode, pageable);

        return PlacesListByPlaceList(places);
    }

    public List<PlaceListResDTO> selectByTagPlaceList(List<String> hashtag, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 12);

        List<Place> places = repository.findAllByCat3InOrderByIdAsc(hashtag, pageable);

        return PlacesListByPlaceList(places);
    }



}
