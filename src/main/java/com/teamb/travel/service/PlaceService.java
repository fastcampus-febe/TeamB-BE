package com.teamb.travel.service;

import com.teamb.travel.entity.Place;
import com.teamb.travel.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository repository;

    public void saveEntity(Place place) {
        repository.save(place);
    }

}
