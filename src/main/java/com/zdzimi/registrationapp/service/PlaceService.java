package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.exception.PlaceNotFoundException;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Place;
import com.zdzimi.registrationapp.repository.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<Place> findPlacesFromInstitution(Institution institution) {
        return new ArrayList<>(institution.getPlaces());
    }
}
