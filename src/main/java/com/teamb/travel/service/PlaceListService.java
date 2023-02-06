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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Comparator;
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
    private List<PlaceListResDTO> placesListByPlaceList(List<Place> placeList) {

        List<PlaceDetail> details = new ArrayList<>();
        List<Double> rates = new ArrayList<>();
        List<String> inOuts = new ArrayList<>();

        for (Place place : placeList) {
            String contentid = place.getContentid();

            details.add(placeDetailRepository.findByContentid(contentid).get());

            Double avgRate = replyRepository.findByPlaceDetailInNativeQuery(contentid);
            if (avgRate == null) {
                rates.add(0.0);
            } else {
                rates.add(avgRate);
            }

            List<IsIndoor> findInOuts = indoorRepository.findAllByMapXAndMapY(place.getMapx().substring(0, 7), place.getMapy().substring(0, 6));
            IsIndoor indoors = findInOuts.get(0);
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

    public Page<PlaceListResDTO> selectPlacesByTitleContaining(PlaceListReqDTO placeListReqDTO, Pageable pageable) {
        PageRequest pageInfo = getPageInfo(pageable);

        Page<PlaceListResDTO> selectByTitlePlacePage = placeRepository.findPlacesByTitleContaining(placeListReqDTO.getTitle(), pageInfo)
                .map(place -> placeTranslate(place));

        return selectByTitlePlacePage;
    }

    public Page<PlaceListResDTO> selectByLocationPlaceList(String areaCode, Pageable pageable) {
        PageRequest pageInfo = getPageInfo(pageable);

        Page<PlaceListResDTO> locationPlacePage = placeRepository.findAllByAreacodeOrderByIdAsc(areaCode, pageInfo)
                .map(place -> placeTranslate(place));
        return locationPlacePage;
    }

    public Page<PlaceListResDTO> selectByTagPlaceList(List<String> hashtag, Pageable pageable) {
        PageRequest pageInfo = getPageInfo(pageable);

        Page<PlaceListResDTO> tagPlacePage = placeRepository.findAllByCat3InOrderByIdAsc(hashtag, pageInfo)
                .map(place -> placeTranslate(place));
        return tagPlacePage;
    }

    public Page<PlaceListResDTO> mainPlaceTourlist(Pageable pageable) {
        PageRequest pageInfo = getPageInfo(pageable);

        Page<PlaceListResDTO> placePage = placeRepository.findAll(pageInfo)
                .map(place -> placeTranslate(place));
        return placePage;
    }

    private PageRequest getPageInfo(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id"));
    }

    private PlaceListResDTO placeTranslate(Place place) {
        String contentid = place.getContentid();

        PlaceDetail placeDetail = placeDetailRepository.findByContentid(contentid).get();


        Double avgRate = replyRepository.findByPlaceDetailInNativeQuery(contentid);
        if (avgRate == null) {
            avgRate = 0.0;
        }

        List<IsIndoor> findInOuts = indoorRepository.findAllByMapXAndMapY(place.getMapx().substring(0, 7), place.getMapy().substring(0, 6));
        IsIndoor indoors;
        String inOut;
        try {
            indoors = findInOuts.get(0);
            inOut = indoors.getInOut();
        } catch (NullPointerException e) {
            inOut = "";
        } catch (IndexOutOfBoundsException e) {
            inOut = "";
        }

        return new PlaceListResDTO(place.getAddr1(), place.getContentid(), place.getTitle(), place.getCat3()
                , place.getFirstimage(), placeDetail.getInfocenter(), avgRate, inOut);
    }

}
    
