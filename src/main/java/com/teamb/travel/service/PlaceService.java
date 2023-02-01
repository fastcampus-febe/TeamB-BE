package com.teamb.travel.service;

import com.teamb.travel.dto.PlaceByLocationResDto;
import com.teamb.travel.dto.PlaceByTagResDto;
import com.teamb.travel.entity.IsIndoor;
import com.teamb.travel.entity.Place;
import com.teamb.travel.entity.PlaceDetail;
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
public class PlaceService {

    private final PlaceRepository repository;
    private final IsIndoorRepository indoorRepository;
    private final PlaceDetailRepository detailRepository;
    private final ReplyRepository replyRepository;

    public void saveEntity(Place place) {
        repository.save(place);
    }

    public List<PlaceByLocationResDto> selectByLocationPlaceList(String areaCode, int pageNo) {
        List<PlaceByLocationResDto> resDtos = new ArrayList<>();
        List<Double> result = new ArrayList<>();
        List<String> inOut = new ArrayList<>();
        List<String> mapX = new ArrayList<>();
        List<String> mapY = new ArrayList<>();

        Pageable pageable = PageRequest.of(pageNo-1, 12);
        List<Place> places = repository.findAllByAreacodeOrderByIdAsc(areaCode, pageable);
        List<String> contentid = places.stream().map(Place::getContentid).collect(Collectors.toList());
        List<PlaceDetail> detailList = detailRepository.findAllByContentidIn(contentid);

        for (int i = 0; i < contentid.size(); i++) {
            mapping(result, inOut, mapX, mapY, places, contentid, i);

            PlaceByLocationResDto dto = PlaceByLocationResDto.builder().addr1(places.get(i).getAddr1()).contentid(places.get(i).getContentid())
                    .title(places.get(i).getTitle()).cat3(places.get(i).getCat3())
                    .infocenter(detailList.get(i).getInfocenter()).firstimage(places.get(i).getFirstimage())
                    .inOut(inOut.get(i)).rate(result.get(i)).build();

            resDtos.add(dto);
        }

        return resDtos;
    }

    public List<PlaceByTagResDto> selectByTagPlaceList(List<String> hashtag, int pageNo) {
        List<PlaceByTagResDto> resDtos = new ArrayList<>();
        List<Double> result = new ArrayList<>();
        List<String> inOut = new ArrayList<>();
        List<String> mapX = new ArrayList<>();
        List<String> mapY = new ArrayList<>();

        Pageable pageable = PageRequest.of(pageNo-1, 12);
        List<Place> places = repository.findAllByCat3InOrderByIdAsc(hashtag, pageable);
        List<String> contentid = places.stream().map(Place::getContentid).collect(Collectors.toList());
        List<PlaceDetail> detailList = detailRepository.findAllByContentidIn(contentid);

        for (int i = 0; i < contentid.size(); i++) {
            mapping(result, inOut, mapX, mapY, places, contentid, i);

            PlaceByTagResDto dto = PlaceByTagResDto.builder().addr1(places.get(i).getAddr1()).contentid(places.get(i).getContentid())
                    .title(places.get(i).getTitle()).cat3(places.get(i).getCat3())
                    .infocenter(detailList.get(i).getInfocenter()).firstimage(places.get(i).getFirstimage())
                    .inOut(inOut.get(i)).rate(result.get(i)).build();

            resDtos.add(dto);
        }

        return resDtos;
    }

    private void mapping(List<Double> result, List<String> inOut, List<String> mapX, List<String> mapY, List<Place> places, List<String> contentid, int i) {
        mapX.add(places.get(i).getMapx().substring(0, 7));
        mapY.add(places.get(i).getMapy().substring(0, 6));

        Double rates = replyRepository.findByPlaceDetailInNativeQuery(contentid.get(i));
        if (rates == null) {
            result.add(i, 0.0);
        } else {
            result.add(i, rates);
        }

        IsIndoor indoors = indoorRepository.findAllByMapXAndMapY(mapX.get(i), mapY.get(i));

        if (indoors == null) {
            inOut.add(i, "");
        } else {
            inOut.add(i, indoors.getInOut());
        }
    }
}
