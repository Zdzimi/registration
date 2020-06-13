package com.zdzimi.registrationapp.controller.representative;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Place;
import com.zdzimi.registrationapp.service.RepresentativeLinkService;
import com.zdzimi.registrationapp.service.entities.InstitutionService;
import com.zdzimi.registrationapp.service.entities.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/registration-app/{institutionName}/places")
@CrossOrigin
public class RegistrationAppPlaceController {

    private InstitutionService institutionService;
    private PlaceService placeService;
    private RepresentativeLinkService representativeLinkService;

    @Autowired
    public RegistrationAppPlaceController(InstitutionService institutionService,
                                          PlaceService placeService,
                                          RepresentativeLinkService representativeLinkService) {
        this.institutionService = institutionService;
        this.placeService = placeService;
        this.representativeLinkService = representativeLinkService;
    }

    @GetMapping
    public List<Place> showPlaces(@PathVariable String institutionName){
        Institution institution = institutionService.findByInstitutionName(institutionName);
        List<Place> places = placeService.findByInstitution(institution);
        representativeLinkService.addLinksToPlaces(places, institutionName);
        return places;
    }

    @PostMapping
    public Place addPlace(@PathVariable String institutionName, @RequestBody Place place){
        Institution institution = institutionService.findByInstitutionName(institutionName);
        place.setInstitution(institution);
        return placeService.save(place);
    }

    @GetMapping("/{placeName}")
    public Set<Place> showPlace(@PathVariable String institutionName,
                                @PathVariable String placeName){
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Place place = placeService.findByInstitutionAndPlaceName(institution, placeName);
        representativeLinkService.addLinksToPlace(place, institutionName);
        return Collections.singleton(place);
    }

    @DeleteMapping("/{placeName}")
    public void deletePlace(@PathVariable String institutionName,
                            @PathVariable String placeName) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        placeService.delete(institution, placeName);
    }

}
