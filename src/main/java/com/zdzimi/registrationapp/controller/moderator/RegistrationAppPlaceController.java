package com.zdzimi.registrationapp.controller.moderator;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Place;
import com.zdzimi.registrationapp.service.InstitutionService;
import com.zdzimi.registrationapp.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration-app/{institutionName}/place")
public class RegistrationAppPlaceController {

    private InstitutionService institutionService;
    private PlaceService placeService;

    @Autowired
    public RegistrationAppPlaceController(InstitutionService institutionService,
                                          PlaceService placeService) {
        this.institutionService = institutionService;
        this.placeService = placeService;
    }

    @GetMapping
    public List<Place> showPlaces(@PathVariable String institutionName){
        Institution institution = institutionService.findInstitution(institutionName);
        return placeService.findPlacesFromInstitution(institution);
    }

    @PostMapping
    public Place addPlace(@PathVariable String institutionName, @RequestBody Place place){
        Institution institution = institutionService.findInstitution(institutionName);
        place.setInstitution(institution);
        return placeService.save(place);
    }

    @GetMapping("/{placeName}")
    public Place showPlace(@PathVariable String institutionName,
                           @PathVariable String placeName){
        Institution institution = institutionService.findInstitution(institutionName);
        return placeService.findPlace(institution, placeName);
    }

}
