package com.teamb.travel.service;

import com.teamb.travel.dto.place.PlaceListReqDTO;
import com.teamb.travel.dto.place.PlaceListResDTO;
import com.teamb.travel.entity.IsIndoor;
import com.teamb.travel.entity.Place;
import com.teamb.travel.entity.PlaceDetail;
import com.teamb.travel.repository.IsIndoorRepository;
import com.teamb.travel.repository.PlaceDetailRepository;
import com.teamb.travel.repository.PlaceRepository;
import com.teamb.travel.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceListService {

    private final PlaceRepository placeRepository;
    private final PlaceDetailRepository placeDetailRepository;
    private final ReplyRepository replyRepository;
    private final IsIndoorRepository indoorRepository;

    public void saveEntity(Place place) {
        placeRepository.save(place);
    }

    // placeList를 매개변수로 받으면 거기에 맞는 관광지 List를 반환하는 메서드
    private List<PlaceListResDTO> PlacesListByPlaceList(List<Place> placeList) {

        List<PlaceDetail> details = new ArrayList<>();
        List<Double> rates = new ArrayList<>();
        List<String> inOuts = new ArrayList<>();

        for ( Place place : placeList) {
            String contentid = place.getContentid();

            details.add(placeDetailRepository.findByContentid(contentid).get());

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

    public List<PlaceListResDTO> selectPlacesByTitleContaining(PlaceListReqDTO placeListReqDTO, Pageable pageable) {

        List<Place> places = placeRepository.findPlacesByTitleContaining(placeListReqDTO.getTitle(), pageable);
        return PlacesListByPlaceList(places);
    }

    public List<PlaceListResDTO> selectByLocationPlaceList(String areaCode, Pageable pageable) {

        List<Place> places = placeRepository.findAllByAreacodeOrderByIdAsc(areaCode, pageable);
        return PlacesListByPlaceList(places);
    }

    public List<PlaceListResDTO> selectByTagPlaceList(List<String> hashtag, Pageable pageable) {

        List<Place> places = placeRepository.findAllByCat3InOrderByIdAsc(hashtag, pageable);
        return PlacesListByPlaceList(places);
    }

    public List<PlaceListResDTO> mainPlaceTourlist(Pageable pageable) {
        Page<Place> pageableFind = placeRepository.findAll(pageable);
        List<PlaceListResDTO> placeListResDTOS = PlacesListByPlaceList(pageableFind.toList());
        return placeListResDTOS;
    }
}
