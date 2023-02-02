package com.teamb.travel.service;

import com.teamb.travel.dto.PlaceListReqDTO;
import com.teamb.travel.dto.PlaceListResDTO;
import com.teamb.travel.entity.Place;
import com.teamb.travel.entity.PlaceDetail;
import com.teamb.travel.entity.Reply;
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

    public void saveEntity(Place place) {
        repository.save(place);
    }

    public List<PlaceListResDTO> selectPlacesByTitleContaining(PlaceListReqDTO placeListReqDTO, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 12);

        List<Place> places = repository.findPlacesByTitleContaining(placeListReqDTO.getTitle(), pageable);
        List<PlaceDetail> details = new ArrayList<>();
        List<Double> rates = new ArrayList<>();

        for ( Place place : places) {
            String contentid = place.getContentid();

            details.add(placeDetailRepository.findByContentid(contentid));

            Double avgRate = replyRepository.findByPlaceDetailInNativeQuery(contentid);
            if (avgRate == null) {
                rates.add(0.0);
            } else {
                rates.add(avgRate);
            }

        }

        List<PlaceListResDTO> placeListResDTOS = new ArrayList<>();
        for (int i = 0; i < places.size(); i++) {
            placeListResDTOS.add(new PlaceListResDTO(places.get(i).getAddr1(), places.get(i).getContentid(),
                    places.get(i).getTitle(), places.get(i).getCat3(), places.get(i).getFirstimage(),
                    details.get(i).getInfocenter(), rates.get(i)));
        }

        return placeListResDTOS;
    }
}
