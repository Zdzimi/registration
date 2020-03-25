package com.zdzimi.registrationapp.service.entities;

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

    public Place findByInstitutionAndPlaceName(Institution institution, String placeName) {
        return placeRepo.findByInstitutionAndPlaceName(institution, placeName)
                .orElseThrow(() -> new PlaceNotFoundException(placeName));
    }

    public List<Place> findByInstitution(Institution institution) {
        return placeRepo.findByInstitution(institution);
    }

    public Place save(Place place) {
        return placeRepo.save(place);
    }
}
