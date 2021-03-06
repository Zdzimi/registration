package com.zdzimi.registrationapp.service.entities;

import com.zdzimi.registrationapp.exception.PlaceNotFoundException;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Place;
import com.zdzimi.registrationapp.model.entities.Visit;
import com.zdzimi.registrationapp.repository.PlaceRepo;
import com.zdzimi.registrationapp.validator.DeletePlaceValidator;
import com.zdzimi.registrationapp.validator.RegistrationAppValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<Place> getPlaceByInstitutionAndPlaceName(Institution institution, String placeName) {
        return placeRepo.findByInstitutionAndPlaceName(institution, placeName);
    }

    public List<Place> findByInstitution(Institution institution) {
        return placeRepo.findByInstitution(institution);
    }

    public Place save(Place place) {
        return placeRepo.save(place);
    }

    public void delete(Institution institution, String placeName) { //  todo - create DeletePlaceValidator
        Place place = findByInstitutionAndPlaceName(institution, placeName);
        RegistrationAppValidator validator = new DeletePlaceValidator(place);
        if (validator.isValid()) {
            placeRepo.delete(place);
        }
    }

    public Place findByInstitutionAndPlaceId(Institution institution, long placeId) {
        return placeRepo.findByInstitutionAndPlaceId(institution, placeId)
                .orElseThrow(() -> new PlaceNotFoundException(institution.getInstitutionName(),placeId));
    }
}
