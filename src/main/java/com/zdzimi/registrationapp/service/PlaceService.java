package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.exception.PlaceNotFoundException;
import com.zdzimi.registrationapp.model.Institution;
import com.zdzimi.registrationapp.model.Place;
import com.zdzimi.registrationapp.repository.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {

    private PlaceRepo placeRepo;

    @Autowired
    public PlaceService(PlaceRepo placeRepo) {
        this.placeRepo = placeRepo;
    }

    public Place findPlace(Institution institution, String placeName){
        return institution.getPlaces().stream()
                .filter(place -> place.getPlaceName().equals(placeName))
                .findFirst().orElseThrow(() -> new PlaceNotFoundException(placeName));
    }

    public Place save(Place place) {
        return placeRepo.save(place);
    }
}
