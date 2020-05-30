package com.zdzimi.registrationapp.controller.representative;

import com.zdzimi.registrationapp.model.*;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Place;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.service.RepresentativeLinkService;
import com.zdzimi.registrationapp.service.entities.InstitutionService;
import com.zdzimi.registrationapp.service.entities.PlaceService;
import com.zdzimi.registrationapp.service.entities.RepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration-app")
@CrossOrigin
public class RegistrationAppController {

    private InstitutionService institutionService;
    private PlaceService placeService;
    private RepresentativeService representativeService;
    private RepresentativeLinkService representativeLinkService;

    @Autowired
    public RegistrationAppController(InstitutionService institutionService,
                                     PlaceService placeService,
                                     RepresentativeService representativeService,
                                     RepresentativeLinkService representativeLinkService) {
        this.institutionService = institutionService;
        this.placeService = placeService;
        this.representativeService = representativeService;
        this.representativeLinkService = representativeLinkService;
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
        Institution institution = institutionService.findByInstitutionName(institutionName);
        representativeLinkService.addLinkToInstitution(institution, institutionName);
        return institution;
    }
}
