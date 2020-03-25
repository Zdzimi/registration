package com.zdzimi.registrationapp.controller.moderator;

import com.zdzimi.registrationapp.model.*;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Place;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.service.entities.InstitutionService;
import com.zdzimi.registrationapp.service.entities.PlaceService;
import com.zdzimi.registrationapp.service.entities.RepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration-app")
public class RegistrationAppController {

    private InstitutionService institutionService;
    private PlaceService placeService;
    private RepresentativeService representativeService;

    @Autowired
    public RegistrationAppController(InstitutionService institutionService,
                                     PlaceService placeService,
                                     RepresentativeService representativeService) {
        this.institutionService = institutionService;
        this.placeService = placeService;
        this.representativeService = representativeService;
    }

    @PostMapping
    public Institution createNewInstitution(@RequestBody InitialObject initialObject){
        Institution institution = initialObject.getInstitution();
        Representative representative = initialObject.getRepresentative();
        Place place = initialObject.getPlace();
        institutionService.save(institution);
        representative.getWorkPlaces().add(institution);
        representativeService.save(representative);
        place.setInstitution(institution);
        placeService.save(place);
        return institutionService.findByInstitutionName(institution.getInstitutionName());
    }

    @GetMapping("/{institutionName}")
    public Institution findInstitutionByName(@PathVariable String institutionName){
        return institutionService.findByInstitutionName(institutionName);
    }
}
