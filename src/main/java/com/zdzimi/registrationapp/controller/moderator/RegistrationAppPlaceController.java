package com.zdzimi.registrationapp.controller.moderator;

import com.zdzimi.registrationapp.model.Institution;
import com.zdzimi.registrationapp.model.Place;
import com.zdzimi.registrationapp.service.InstitutionService;
import com.zdzimi.registrationapp.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
    public Set<Place> showPlaces(@PathVariable String institutionName){
        return institutionService.findInstitution(institutionName).getPlaces();
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
